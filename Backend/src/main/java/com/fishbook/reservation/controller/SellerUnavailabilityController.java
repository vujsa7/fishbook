package com.fishbook.reservation.controller;

import com.fishbook.reservation.dto.DateRangeDto;
import com.fishbook.reservation.model.SellerUnavailability;
import com.fishbook.reservation.service.SellerUnavailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/sellerUnavailability")
@RequiredArgsConstructor
public class SellerUnavailabilityController {

    private final SellerUnavailabilityService sellerUnavailabilityService;

    @GetMapping(value = "/{entityId}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity getSellerUnavailabilityForEntity(@PathVariable Long entityId){
        List<SellerUnavailability> sellerUnavailabilityList = sellerUnavailabilityService.getSellerUnavailabilityForEntity(entityId);
        if(sellerUnavailabilityList != null){
            List<DateRangeDto> unavailability = sellerUnavailabilityList.stream().map(u -> new DateRangeDto(u.getFromDateTime(), u.getToDateTime())).collect(Collectors.toList());
            return new ResponseEntity(unavailability, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

}
