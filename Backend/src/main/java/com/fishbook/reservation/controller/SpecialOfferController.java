package com.fishbook.reservation.controller;

import com.fishbook.reservation.dto.SellerAvailabilityDetailsDto;
import com.fishbook.reservation.dto.SpecialOfferDetailsDto;
import com.fishbook.reservation.dto.SpecialOfferDto;
import com.fishbook.reservation.model.SpecialOffer;
import com.fishbook.reservation.service.SpecialOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/specialOffers")
@RequiredArgsConstructor
public class SpecialOfferController {

    private final SpecialOfferService specialOfferService;

    @GetMapping
    @PreAuthorize("hasAnyRole('BOAT_OWNER', 'INSTRUCTOR', 'HOUSE_OWNER', 'CLIENT', 'ADMIN')")
    public ResponseEntity<List<SpecialOfferDetailsDto>> getSpecialOffers(@RequestParam(value = "entityId") Long entityId) {
        return new ResponseEntity<>(specialOfferService.getAll(entityId).stream()
                .map(specialOffer -> new SpecialOfferDetailsDto(specialOffer.getId(), specialOffer.getStartDateTime(), specialOffer.getEndDateTime(), specialOffer.getMaxNumberOfPeople(),
                        specialOffer.getAdditionalServices(), specialOffer.getEntity().getId(), specialOffer.getDiscount(), specialOffer.getVersion()))
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('BOAT_OWNER', 'INSTRUCTOR', 'HOUSE_OWNER')")
    public ResponseEntity<?> createSpecialOffer(@RequestBody SpecialOfferDto specialOfferDto) {
        specialOfferService.save(SpecialOffer.builder()
                .startDateTime(specialOfferDto.getStartDateTime())
                .endDateTime(specialOfferDto.getEndDateTime())
                .maxNumberOfPeople(specialOfferDto.getMaxNumberOfPeople())
                .additionalServices(specialOfferDto.getAdditionalServices())
                .discount(specialOfferDto.getDiscount())
                .build(), specialOfferDto.getEntityId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
