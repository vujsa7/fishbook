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
import com.fishbook.entity.dto.EntityBasicInfoDto;
import com.fishbook.location.model.Address;
import com.fishbook.location.model.City;
import com.fishbook.location.service.LocationService;
import com.fishbook.storage.service.StorageService;
import com.fishbook.user.model.User;
import com.fishbook.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/boats")
public class BoatController {
    private final BoatService boatService;
    private final RuleService ruleService;
    private final EquipmentService equipmentService;
    private final LocationService locationService;
    private final UserService userService;
    private final AdditionalServiceService additionalServiceService;
    private final StorageService storageService;

    @Autowired
    public BoatController(BoatService boatService, RuleService ruleService, EquipmentService equipmentService, LocationService locationService, UserService userService, AdditionalServiceService additionalServiceService, StorageService storageService) {
        this.boatService = boatService;
        this.ruleService = ruleService;
        this.equipmentService = equipmentService;
        this.locationService = locationService;
        this.userService = userService;
        this.additionalServiceService = additionalServiceService;
        this.storageService = storageService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_BOAT_OWNER')")
    public ResponseEntity<Long> registerNewBoat(@RequestBody BoatRegistrationDto boatRegistrationDto,  Principal principal){
        try {
            City city = locationService.findCityByName(boatRegistrationDto.getCity());
            Address address = new Address(boatRegistrationDto.getAddress(), city, 0.0, 0.0);
            User user = userService.findByEmail(principal.getName());
            HashSet<AdditionalService> additionalServices = additionalServiceService.saveAll(boatRegistrationDto.getAdditionalServices());

            Boat boat = new Boat(boatRegistrationDto.getName(), boatRegistrationDto.getDescription(), boatRegistrationDto.getAdvancePayment(), boatRegistrationDto.getPrice(),
                    false, address, boatRegistrationDto.getAppliedRules(), additionalServices,
                    boatRegistrationDto.getLength(), boatRegistrationDto.getMotors(), boatRegistrationDto.getPower(),
                    boatRegistrationDto.getMaxSpeed(), boatRegistrationDto.getMaxPeople(), boatRegistrationDto.getLoadCapacity(),
                    boatRegistrationDto.getFuelConsumption(), boatRegistrationDto.getMaxDistance(), boatRegistrationDto.getEnergyConsumption(),
                    BoatType.valueOf(boatRegistrationDto.getBoatType()), user, boatRegistrationDto.getEquipment());
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

    @GetMapping
    public ResponseEntity getAllBoats(){
        List<EntityBasicInfoDto> boats = boatService.getAll().stream()
                .map(boat -> new EntityBasicInfoDto(boat.getId(), storageService.getPriorityImageUrl(boat.getImages()), boat.getName(), boat.getDescription(),
                        boat.getPricePerDay(), boat.getAddress().getCity().getName() + ", " + boat.getAddress().getCity().getCountry().getName(),
                        boat.getOwner().getFirstName() + " " + boat.getOwner().getLastName()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(boats, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BOAT_OWNER')")
    public ResponseEntity deleteBoat(@PathVariable Long id, Authentication authentication){
        Optional<Boat> boat = boatService.findById(id);
        if(boat.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User userDetails = (User) authentication.getPrincipal();
        if(Objects.equals(userDetails.getRole().getName(), "ROLE_BOAT_OWNER") && !Objects.equals(boat.get().getOwner().getId(), userDetails.getId())){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        boatService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
