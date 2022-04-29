package com.fishbook.boat.service;


import com.fishbook.boat.dto.BoatRegistrationDto;
import com.fishbook.boat.model.Boat;

public interface BoatService {
    Long saveNewBoat(BoatRegistrationDto boatRegistrationDto, String username);
}
