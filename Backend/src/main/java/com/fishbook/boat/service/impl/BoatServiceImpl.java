package com.fishbook.boat.service.impl;

import com.fishbook.additional.entity.information.dao.AdditionalServiceRepository;
import com.fishbook.boat.dao.BoatRepository;
import com.fishbook.boat.dto.BoatRegistrationDto;
import com.fishbook.additional.entity.information.model.AdditionalService;
import com.fishbook.boat.model.Boat;
import com.fishbook.boat.model.BoatType;
import com.fishbook.boat.service.BoatService;
import com.fishbook.location.model.Address;
import com.fishbook.location.model.City;
import com.fishbook.location.service.LocationService;
import com.fishbook.user.dao.UserRepository;
import com.fishbook.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class BoatServiceImpl implements BoatService {
    private final BoatRepository boatRepository;
    private final AdditionalServiceRepository additionalServiceRepository;
    private final UserRepository userRepository;

    private final LocationService locationService;

    @Autowired
    public BoatServiceImpl(BoatRepository boatRepository, AdditionalServiceRepository additionalServiceRepository, UserRepository userRepository, LocationService locationService) {
        this.boatRepository = boatRepository;
        this.additionalServiceRepository = additionalServiceRepository;
        this.userRepository = userRepository;
        this.locationService = locationService;
    }

    @Override
    public Long saveNewBoat(BoatRegistrationDto boatRegistrationDto, String username) {
        City city = locationService.findCityByName(boatRegistrationDto.getCity());
        Address address = new Address(boatRegistrationDto.getAddress(), city, 0.0, 0.0);
        User user = userRepository.findByEmail(username);

        Boat boat = new Boat(boatRegistrationDto.getName(), boatRegistrationDto.getDescription(), address,
                boatRegistrationDto.getLength(), boatRegistrationDto.getMotors(), boatRegistrationDto.getPower(),
                boatRegistrationDto.getMaxSpeed(), boatRegistrationDto.getMaxPeople(), boatRegistrationDto.getLoadCapacity(),
                boatRegistrationDto.getFuelConsumption(), boatRegistrationDto.getMaxDistance(), boatRegistrationDto.getEnergyConsumption(),
                boatRegistrationDto.getAdvancePayment(), boatRegistrationDto.getPrice(), BoatType.valueOf(boatRegistrationDto.getBoatType()), user);
        boat.setAppliedRules(boatRegistrationDto.getAppliedRules());
        boat.setEquipment(boatRegistrationDto.getEquipment());
        List<AdditionalService> additionalServices = additionalServiceRepository.saveAll(boatRegistrationDto.getAdditionalServices());
        HashSet<AdditionalService> additionalServiceSet = new HashSet<>(additionalServices);
        boat.setAdditionalServices(additionalServiceSet);

        return boatRepository.save(boat).getId();
    }

}
