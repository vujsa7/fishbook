package com.fishbook.reservation.controller;

import com.fishbook.reservation.dto.SpecialOfferDto;
import com.fishbook.reservation.model.SpecialOffer;
import com.fishbook.reservation.service.SpecialOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/specialOffers")
@RequiredArgsConstructor
public class SpecialOfferController {

    private final SpecialOfferService specialOfferService;

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
