package com.fishbook.reservation.service;

import com.fishbook.reservation.model.EntityAvailability;

import java.util.List;

public interface EntityAvailabilityService {
    void save(EntityAvailability entityAvailability, Long entityId);
    List<EntityAvailability> getAllEntitiesAvailabilities();
    List<EntityAvailability> getAvailabilityForEntity(Long id);
}
