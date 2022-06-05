package com.fishbook.reservation.controller;

import com.fishbook.reservation.dto.SellerAvailabilityDetailsDto;
import com.fishbook.reservation.dto.DateRangeDto;
import com.fishbook.reservation.dto.SellerAvailabilityDto;
import com.fishbook.reservation.dto.SellerAvailabilityPreviewDto;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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

    @GetMapping(value = "/all")
    public ResponseEntity getAllSellerAvailabilities(){
        List<SellerAvailability> availabilities = sellerAvailabilityService.getAllSellerAvailabilities();
        HashMap<String, ArrayList<DateRangeDto>> availabilityBySeller = new HashMap<String, ArrayList<DateRangeDto>>();
        for (SellerAvailability a : availabilities){
            if(availabilityBySeller.containsKey(a.getSeller().getEmail()))
                availabilityBySeller.get(a.getSeller().getEmail()).add(new DateRangeDto(a.getFromDateTime(), a.getToDateTime()));
            else
                availabilityBySeller.put(a.getSeller().getEmail(), new ArrayList<DateRangeDto>(Arrays.asList(new DateRangeDto(a.getFromDateTime(), a.getToDateTime()))));
        }
        List<SellerAvailabilityPreviewDto> sellerAvailabilitiesDtos = new ArrayList<>();
        availabilityBySeller.forEach((email, value) -> {
            sellerAvailabilitiesDtos.add(new SellerAvailabilityPreviewDto(email, value));
        });
        return new ResponseEntity(sellerAvailabilitiesDtos, HttpStatus.OK);
    }
}
