package com.fishbook.reservation.dao;

import com.fishbook.reservation.model.SellerUnavailability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellerUnavailabilityRepository extends JpaRepository<SellerUnavailability, Long> {

    List<SellerUnavailability> findAllBySellerId(Long sellerId);
}
