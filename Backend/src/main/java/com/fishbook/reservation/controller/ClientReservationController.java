package com.fishbook.reservation.controller;
import com.fishbook.reservation.dto.ClientReservationDto;
import com.fishbook.reservation.model.Reservation;
import com.fishbook.storage.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.fishbook.additional.entity.information.model.AdditionalService;
import com.fishbook.entity.model.Entity;
import com.fishbook.exception.ApiRequestException;
import com.fishbook.reservation.dto.CreateReservationDto;
import com.fishbook.reservation.dto.DateRangeDto;
import com.fishbook.reservation.dto.EntityOfferDetailsDto;
import com.fishbook.reservation.model.EntityAvailability;
import com.fishbook.reservation.model.ReservationCandidate;
import com.fishbook.reservation.model.SellerAvailability;
import com.fishbook.reservation.service.EntityAvailabilityService;
import com.fishbook.reservation.service.SellerAvailabilityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fishbook.reservation.service.ClientReservationService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/clientReservations")
@RequiredArgsConstructor
public class ClientReservationController {

    private final ClientReservationService clientReservationService;
    private final SellerAvailabilityService sellerAvailabilityService;
    private final EntityAvailabilityService entityAvailabilityService;
    private final StorageService storageService;

    @GetMapping(value = "/details/{entityId}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity getReservationOfferDetails(@PathVariable Long entityId){
        Entity entity = clientReservationService.getReservationOfferDetails(entityId);
        if(entity == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        List<DateRangeDto> dateRangeDtos = new ArrayList<>();
        if (entity.getClass().getName().contains("FishingLesson")) {
            List<SellerAvailability> sellerAvailability = sellerAvailabilityService.getAvailabilityForSeller(entity.getOwner().getId());
            dateRangeDtos = sellerAvailability.stream().map(a -> new DateRangeDto(a.getFromDateTime(), a.getToDateTime())).collect(Collectors.toList());
        } else {
            List<EntityAvailability> entityAvailability = entityAvailabilityService.getAvailabilityForEntity(entity.getId());
            dateRangeDtos = entityAvailability.stream().map(a -> new DateRangeDto(a.getFromDateTime(), a.getToDateTime())).collect(Collectors.toList());
        }
        Set<AdditionalService> additionalServices = entity.getAdditionalServices();
        return new ResponseEntity(new EntityOfferDetailsDto(entityId, entity.getName(), entity.getPricePerDay(), additionalServices == null ? Collections.emptySet() : additionalServices, dateRangeDtos), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity postClientReservation(Principal principal, @RequestBody CreateReservationDto createReservationDto){
        try{
            ReservationCandidate reservationCandidate = new ReservationCandidate(createReservationDto.getStart(), createReservationDto.getEnd(), createReservationDto.getTotalPrice(),
                    createReservationDto.getAdditionalServices(), principal.getName(), createReservationDto.getEntityId());
            clientReservationService.createReservation(reservationCandidate);
            return new ResponseEntity(reservationCandidate, HttpStatus.CREATED);
        } catch (ApiRequestException e){
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/history")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity getReservationHistory(Principal principal){
        List<Reservation> reservationHistory = clientReservationService.getReservationHistory(principal.getName());
        List<ClientReservationDto> clientReservationDtos = reservationHistory.stream().map(r -> new ClientReservationDto(r.getId(), storageService.getPriorityImageUrl(r.getEntity().getImages()), r.getEntity().getName(),
                r.getEntity().getClass().getName(), r.getStartDateTime(), r.getEndDateTime(), r.getPrice(),
                clientReservationService.getStatus(r.getIsCancelled(), r.getStartDateTime(), r.getEndDateTime()), r.getEntity().getId())).collect(Collectors.toList());
        return new ResponseEntity(clientReservationDtos, HttpStatus.OK);
    }
}