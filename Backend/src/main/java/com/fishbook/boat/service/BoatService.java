package com.fishbook.boat.service;

import com.fishbook.boat.model.Boat;

import java.util.List;
import java.util.Optional;

public interface BoatService {
    Optional<Boat> findById(Long id);
    Long saveNewBoat(Boat boat);
    void deleteById(Long id);
    List<Boat> getAll();
}
