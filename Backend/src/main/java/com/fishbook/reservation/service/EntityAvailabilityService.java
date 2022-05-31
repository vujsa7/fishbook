package com.fishbook.reservation.service;

import com.fishbook.reservation.model.EntityAvailability;

public interface EntityAvailabilityService {
    void save(EntityAvailability entityAvailability, Long entityId);
}
