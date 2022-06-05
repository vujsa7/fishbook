package com.fishbook.reservation.controller;

import com.fishbook.reservation.dto.DateRangeDto;
import com.fishbook.reservation.dto.EntityAvailabilityDto;
import com.fishbook.reservation.dto.EntityAvailabilityPreviewDto;
import com.fishbook.reservation.dto.SellerAvailabilityPreviewDto;
import com.fishbook.reservation.model.EntityAvailability;
import com.fishbook.reservation.model.SellerAvailability;
import com.fishbook.reservation.service.EntityAvailabilityService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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

    @GetMapping(value = "/all")
    public ResponseEntity getAllEntitiesAvailabilities(){
        List<EntityAvailability> availabilities = entityAvailabilityService.getAllEntitiesAvailabilities();
        HashMap<Long, ArrayList<DateRangeDto>> availabilityByEntity = new HashMap<Long, ArrayList<DateRangeDto>>();
        for (EntityAvailability a : availabilities){
            if(availabilityByEntity.containsKey(a.getEntity().getId()))
                availabilityByEntity.get(a.getEntity().getId()).add(new DateRangeDto(a.getFromDateTime(), a.getToDateTime()));
            else
                availabilityByEntity.put(a.getEntity().getId(), new ArrayList<DateRangeDto>(Arrays.asList(new DateRangeDto(a.getFromDateTime(), a.getToDateTime()))));
        }
        List<EntityAvailabilityPreviewDto> entityAvailabilities = new ArrayList<>();
        availabilityByEntity.forEach((id, value) -> {
            entityAvailabilities.add(new EntityAvailabilityPreviewDto(id, value));
        });
        return new ResponseEntity(entityAvailabilities, HttpStatus.OK);
    }
}
