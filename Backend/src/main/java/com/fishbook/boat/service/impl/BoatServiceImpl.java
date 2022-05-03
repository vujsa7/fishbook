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
    public Long saveNewBoat(Boat boat) {
        return boatRepository.save(boat).getId();
    }

}
