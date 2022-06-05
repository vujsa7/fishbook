package com.fishbook.reservation.service;

import com.fishbook.reservation.model.SellerAvailability;

import java.util.List;

public interface SellerAvailabilityService {
    void save(SellerAvailability sellerAvailability);
    List<SellerAvailability> getAll(Long sellerId);
    List<SellerAvailability> getAllSellerAvailabilities();
    List<SellerAvailability> getAvailabilityForSeller(Long id);
}
