package com.fishbook.additional.entity.information.controller;


import com.fishbook.additional.entity.information.model.Equipment;
import com.fishbook.additional.entity.information.service.EquipmentService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/api/equipment")
@RequiredArgsConstructor
public class EquipmentController {

    private final EquipmentService equipmentService;

    @GetMapping
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'BOAT_OWNER')")
    public List<Equipment> getEquipment(@RequestParam String type){
        if(Objects.equals(type, "boat")){
            return equipmentService.getBoatEquipment();
        }
        return equipmentService.getFishingEquipment();
    }

}
