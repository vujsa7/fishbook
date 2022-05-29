package com.fishbook.reservation.dao;

import com.fishbook.reservation.model.SellerAvailability;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerAvailabilityRepository extends JpaRepository<SellerAvailability, Long> {
}
