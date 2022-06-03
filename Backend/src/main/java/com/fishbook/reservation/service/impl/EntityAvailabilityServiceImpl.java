package com.fishbook.reservation.service.impl;

import com.fishbook.entity.dao.EntityRepository;
import com.fishbook.entity.model.Entity;
import com.fishbook.exception.ApiRequestException;
import com.fishbook.exception.DateTimeRangeOverlappingException;
import com.fishbook.reservation.dao.EntityAvailabilityRepository;
import com.fishbook.reservation.model.EntityAvailability;
import com.fishbook.reservation.service.EntityAvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EntityAvailabilityServiceImpl implements EntityAvailabilityService {

    private final EntityAvailabilityRepository entityAvailabilityRepository;
    private final EntityRepository entityRepository;

    @Override
    public void save(EntityAvailability entityAvailability, Long entityId) {
        Entity entity = entityRepository.findById(entityId).orElseThrow(() -> new ApiRequestException("Entity with given id not found"));
        entityAvailability.setEntity(entity);

        List<EntityAvailability> entityAvailabilities = entityAvailabilityRepository.findAllByEntityId(entityAvailability.getEntity().getId());
        if (entityAvailabilities.stream().anyMatch(s -> s.isOverlapping(entityAvailability))) {
            throw new DateTimeRangeOverlappingException();
        }

        entityAvailabilityRepository.save(entityAvailability);
    }

    @Override
    public List<EntityAvailability> getAllEntitiesAvailabilities() {
        return entityAvailabilityRepository.findAll();
    }

    @Override
    public List<EntityAvailability> getAvailabilityForEntity(Long id) {
        return entityAvailabilityRepository.findAllByEntityId(id);
    }
}
