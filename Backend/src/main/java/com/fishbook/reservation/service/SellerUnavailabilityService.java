package com.fishbook.reservation.service;

import com.fishbook.reservation.model.SellerUnavailability;

import java.util.List;

public interface SellerUnavailabilityService {

    List<SellerUnavailability> getSellerUnavailabilityForEntity(Long entityId);
}
