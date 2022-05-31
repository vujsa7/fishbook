package com.fishbook.reservation.controller;

import com.fishbook.reservation.dto.SellerAvailabilityDetailsDto;
import com.fishbook.reservation.dto.SellerAvailabilityDto;
import com.fishbook.reservation.model.SellerAvailability;
import com.fishbook.reservation.service.SellerAvailabilityService;
import com.fishbook.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/sellerAvailabilities")
@RequiredArgsConstructor
public class SellerAvailabilityController {

    private final SellerAvailabilityService sellerAvailabilityService;

    @GetMapping
    public ResponseEntity<List<SellerAvailabilityDetailsDto>> getAvailabilities(@RequestParam(value = "sellerId") Long sellerId) {
        return new ResponseEntity<>(sellerAvailabilityService.getAll(sellerId).stream()
                .map(sellerAvailability -> new SellerAvailabilityDetailsDto(sellerAvailability.getId(), sellerAvailability.getFromDateTime(), sellerAvailability.getToDateTime(),
                        sellerAvailability.getVersion(), sellerAvailability.getSeller().getId()))
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('BOAT_OWNER', 'INSTRUCTOR')")
    public ResponseEntity<?> createAvailability(@RequestBody SellerAvailabilityDto sellerAvailability, Authentication authentication) {
        User userDetails = (User) authentication.getPrincipal();
        sellerAvailabilityService.save(new SellerAvailability(sellerAvailability.getFromDateTime(), sellerAvailability.getToDateTime(), userDetails));

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
