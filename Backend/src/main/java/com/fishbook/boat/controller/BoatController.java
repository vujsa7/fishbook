package com.fishbook.boat.controller;

import com.fishbook.boat.dto.BoatRegistrationDto;
import com.fishbook.additional.entity.information.model.AppliedRule;
import com.fishbook.boat.model.Boat;
import com.fishbook.additional.entity.information.model.Equipment;
import com.fishbook.additional.entity.information.service.AppliedRuleService;
import com.fishbook.boat.service.BoatService;
import com.fishbook.additional.entity.information.service.EquipmentService;
import com.fishbook.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boats")
public class BoatController {
    private final BoatService boatService;
    private final AppliedRuleService appliedRuleService;
    private final EquipmentService equipmentService;
    private TokenUtils tokenUtils;

    @Autowired
    public BoatController(BoatService boatService, AppliedRuleService appliedRuleService, EquipmentService equipmentService, TokenUtils tokenUtils) {
        this.boatService = boatService;
        this.appliedRuleService = appliedRuleService;
        this.equipmentService = equipmentService;
        this.tokenUtils = tokenUtils;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_BOAT_OWNER')")
    public ResponseEntity<Long> registerNewBoat(@RequestBody BoatRegistrationDto boatRegistrationDto,  @RequestHeader (name="Authorization") String token){
        try {
            String username = tokenUtils.getUsernameFromToken(token.substring(7));
            Long boatId = boatService.saveNewBoat(boatRegistrationDto, username);
            return new ResponseEntity<>(boatId, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/rules", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_BOAT_OWNER')")
    public List<AppliedRule> getBoatRules(){
        return appliedRuleService.getBoatRules();
    }

    @GetMapping(value = "/equipment", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_BOAT_OWNER')")
    public List<Equipment> getBoatEquipment(){
        return equipmentService.getBoatEquipment();
    }
}
