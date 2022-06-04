package com.fishbook.reservation.controller;

import com.fishbook.reservation.dto.SellerReservationRequestDto;
import com.fishbook.reservation.model.Reservation;
import com.fishbook.reservation.service.SellerReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping(value = "/api/sellerReservations")
@RequiredArgsConstructor
public class SellerReservationController {

    private final SellerReservationService reservationService;

    @PostMapping
    @PreAuthorize("hasAnyRole('BOAT_OWNER', 'HOUSE_OWNER', 'INSTRUCTOR')")
    public ResponseEntity<?> createReservation(@RequestBody SellerReservationRequestDto dto) throws InterruptedException {
        reservationService.createReservation(new Reservation(dto.getStartDateTime(), dto.getEndDateTime(),
                dto.getMaxNumberOfPeople(), Set.copyOf(dto.getAdditionalServices())), dto.getClientId(), dto.getEntityId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
