package com.fishbook.reservation.service.impl;

import com.fishbook.boat.dao.BoatRepository;
import com.fishbook.boat.model.Boat;
import com.fishbook.reservation.dao.SellerUnavailabilityRepository;
import com.fishbook.reservation.model.SellerUnavailability;
import com.fishbook.reservation.service.SellerUnavailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SellerUnavailabilityServiceImpl implements SellerUnavailabilityService {

    private final SellerUnavailabilityRepository sellerUnavailabilityRepository;
    private final BoatRepository boatRepository;

    @Transactional
    @Override
    public List<SellerUnavailability> getSellerUnavailabilityForEntity(Long entityId) {
        Optional<Boat> boat = boatRepository.findById(entityId);
        if(boat.isEmpty())
            return null;

        return sellerUnavailabilityRepository.findSellerUnavailability(boat.get().getOwner().getId());
    }
}
