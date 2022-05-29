package com.fishbook.reservation.controller;

import com.fishbook.reservation.dto.SellerAvailabilityDto;
import com.fishbook.reservation.model.SellerAvailability;
import com.fishbook.reservation.service.SellerAvailabilityService;
import com.fishbook.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/sellerAvailabilities")
@RequiredArgsConstructor
public class SellerAvailabilityController {

    private final SellerAvailabilityService sellerAvailabilityService;

    @PostMapping
    @PreAuthorize("hasAnyRole('BOAT_OWNER', 'INSTRUCTOR')")
    public ResponseEntity<?> createAvailability(@RequestBody SellerAvailabilityDto sellerAvailability, Authentication authentication) {
        User userDetails = (User) authentication.getPrincipal();
        sellerAvailabilityService.save(new SellerAvailability(sellerAvailability.getFromDateTime(), sellerAvailability.getToDateTime(), userDetails));

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
