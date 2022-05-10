package com.fishbook.house.controller;

import com.fishbook.additional.entity.information.model.AdditionalService;
import com.fishbook.additional.entity.information.model.Rule;
import com.fishbook.additional.entity.information.service.AdditionalServiceService;
import com.fishbook.additional.entity.information.service.RuleService;
import com.fishbook.house.dto.HouseRegistrationDto;
import com.fishbook.house.model.House;
import com.fishbook.house.model.Room;
import com.fishbook.house.service.HouseService;
import com.fishbook.location.model.Address;
import com.fishbook.location.model.City;
import com.fishbook.location.service.LocationService;
import com.fishbook.user.model.User;
import com.fishbook.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/api/houses")
public class HouseController {
    private final HouseService houseService;
    private final RuleService ruleService;
    private final LocationService locationService;
    private final UserService userService;
    private final AdditionalServiceService additionalServiceService;

    @Autowired
    public HouseController(HouseService houseService, RuleService ruleService, LocationService locationService, UserService userService, AdditionalServiceService additionalServiceService) {
        this.houseService = houseService;
        this.ruleService = ruleService;
        this.locationService = locationService;
        this.userService = userService;
        this.additionalServiceService = additionalServiceService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_HOUSE_OWNER')")
    public ResponseEntity registerNewHouse(@RequestBody HouseRegistrationDto houseRegistrationDto, Principal principal){
        try {
            City city = locationService.findCityByName(houseRegistrationDto.getCity());
            Address address = new Address(houseRegistrationDto.getAddress(), city, 0.0, 0.0);
            User user = userService.findByEmail(principal.getName());
            HashSet<AdditionalService> additionalServices = additionalServiceService.saveAll(houseRegistrationDto.getAdditionalServices());

            House house = new House(houseRegistrationDto.getName(), houseRegistrationDto.getDescription(), houseRegistrationDto.getCancellationFee(),
                    houseRegistrationDto.getPrice(), false, address, houseRegistrationDto.getAppliedRules(), additionalServices,
                    houseRegistrationDto.getMaxPeople(), user, houseRegistrationDto.getRooms());
            Long houseId = houseService.saveNewHouse(house);
            return new ResponseEntity<>(houseId, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/rules", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_HOUSE_OWNER')")
    public List<Rule> getHouseRules(){
        return ruleService.getRules("house");
    }
}
