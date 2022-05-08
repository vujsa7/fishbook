package com.fishbook.boat.controller;

import com.fishbook.additional.entity.information.model.AdditionalService;
import com.fishbook.additional.entity.information.service.AdditionalServiceService;
import com.fishbook.boat.dto.BoatRegistrationDto;
import com.fishbook.additional.entity.information.model.Rule;
import com.fishbook.additional.entity.information.model.Equipment;
import com.fishbook.additional.entity.information.service.RuleService;
import com.fishbook.boat.model.Boat;
import com.fishbook.boat.model.BoatType;
import com.fishbook.boat.service.BoatService;
import com.fishbook.additional.entity.information.service.EquipmentService;
import com.fishbook.location.model.Address;
import com.fishbook.location.model.City;
import com.fishbook.location.service.LocationService;
import com.fishbook.user.model.User;
import com.fishbook.user.service.UserService;
import com.fishbook.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/api/boats")
public class BoatController {
    private final BoatService boatService;
    private final RuleService ruleService;
    private final EquipmentService equipmentService;
    private final LocationService locationService;
    private final UserService userService;
    private final AdditionalServiceService additionalServiceService;
    private TokenUtils tokenUtils;

    @Autowired
    public BoatController(BoatService boatService, RuleService ruleService, EquipmentService equipmentService, LocationService locationService, UserService userService, AdditionalServiceService additionalServiceService, TokenUtils tokenUtils) {
        this.boatService = boatService;
        this.ruleService = ruleService;
        this.equipmentService = equipmentService;
        this.locationService = locationService;
        this.userService = userService;
        this.additionalServiceService = additionalServiceService;
        this.tokenUtils = tokenUtils;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_BOAT_OWNER')")
    public ResponseEntity<Long> registerNewBoat(@RequestBody BoatRegistrationDto boatRegistrationDto,  @RequestHeader (name="Authorization") String token){
        try {
            City city = locationService.findCityByName(boatRegistrationDto.getCity());
            Address address = new Address(boatRegistrationDto.getAddress(), city, 0.0, 0.0);
            String username = tokenUtils.getUsernameFromToken(token.substring(7));
            User user = userService.findByEmail(username);
            HashSet<AdditionalService> additionalServices = additionalServiceService.saveAll(boatRegistrationDto.getAdditionalServices());

            Boat boat = new Boat(boatRegistrationDto.getName(), boatRegistrationDto.getDescription(), boatRegistrationDto.getCancellationFee(), boatRegistrationDto.getPrice(),
                    false, address, boatRegistrationDto.getAppliedRules(), additionalServices,
                    boatRegistrationDto.getLength(), boatRegistrationDto.getMotors(), boatRegistrationDto.getPower(),
                    boatRegistrationDto.getMaxSpeed(), boatRegistrationDto.getMaxPeople(), boatRegistrationDto.getLoadCapacity(),
                    boatRegistrationDto.getFuelConsumption(), boatRegistrationDto.getMaxDistance(), boatRegistrationDto.getEnergyConsumption(),
                    0, BoatType.valueOf(boatRegistrationDto.getBoatType()), user, boatRegistrationDto.getEquipment());
            Long boatId = boatService.saveNewBoat(boat);
            return new ResponseEntity<>(boatId, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/rules", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_BOAT_OWNER')")
    public List<Rule> getBoatRules(){
        return ruleService.getRules("boat");
    }

    @GetMapping(value = "/equipment", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_BOAT_OWNER')")
    public List<Equipment> getBoatEquipment(){
        return equipmentService.getBoatEquipment();
    }
}
