package com.fishbook.reservation.controller;

import com.fishbook.reservation.dto.EntityAvailabilityDto;
import com.fishbook.reservation.model.EntityAvailability;
import com.fishbook.reservation.service.EntityAvailabilityService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/entityAvailabilities")
@RequiredArgsConstructor
public class EntityAvailabilityController {

    private final EntityAvailabilityService entityAvailabilityService;

    @PostMapping
    @PreAuthorize("hasAnyRole('BOAT_OWNER', 'HOUSE_OWNER')")
    public ResponseEntity<?> createAvailability(@RequestBody EntityAvailabilityDto entityAvailabilityDto) {
        entityAvailabilityService.save(new EntityAvailability(entityAvailabilityDto.getFromDateTime(), entityAvailabilityDto.getToDateTime()), entityAvailabilityDto.getEntityId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
