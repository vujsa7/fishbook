package com.fishbook.reservation.dao;

import com.fishbook.reservation.model.SellerAvailability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellerAvailabilityRepository extends JpaRepository<SellerAvailability, Long> {

    List<SellerAvailability> findAllBySellerId(Long sellerId);
}
