package com.fishbook.house.service;

import com.fishbook.house.model.House;
import com.fishbook.house.model.Room;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public interface HouseService {
    Optional<House> findById(Long id);
    Long save(House house);
    List<House> getAll();
    List<House> getAllByOwnerUsername(String username);
    void deleteById(Long id);
}
