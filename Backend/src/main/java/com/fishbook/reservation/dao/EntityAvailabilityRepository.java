package com.fishbook.reservation.dao;

import com.fishbook.reservation.model.EntityAvailability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntityAvailabilityRepository extends JpaRepository<EntityAvailability, Long> {

    List<EntityAvailability> findAllByEntityId(Long entityId);
}
