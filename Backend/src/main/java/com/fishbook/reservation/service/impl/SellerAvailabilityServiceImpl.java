package com.fishbook.reservation.service.impl;

import com.fishbook.exception.DateTimeRangeOverlappingException;
import com.fishbook.reservation.dao.SellerAvailabilityRepository;
import com.fishbook.reservation.model.SellerAvailability;
import com.fishbook.reservation.service.SellerAvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerAvailabilityServiceImpl implements SellerAvailabilityService {

    private final SellerAvailabilityRepository sellerAvailabilityRepository;

    @Override
    public void save(SellerAvailability sellerAvailability) {
        List<SellerAvailability> sellerAvailabilities = sellerAvailabilityRepository.findAllBySellerId(sellerAvailability.getSeller().getId());
        if (sellerAvailabilities.stream().anyMatch(s -> s.isOverlapping(sellerAvailability))) {
            throw new DateTimeRangeOverlappingException();
        }
        sellerAvailabilityRepository.save(sellerAvailability);
    }

    @Override
    public List<SellerAvailability> getAll(Long sellerId) {
        return sellerAvailabilityRepository.findAllBySellerId(sellerId);
    }

    @Override
    public List<SellerAvailability> getAllSellerAvailabilities() {
        return sellerAvailabilityRepository.findAll();
    }

    @Override
    public List<SellerAvailability> getAvailabilityForSeller(Long id) {
        return sellerAvailabilityRepository.findAllBySellerId(id);
    }

}
