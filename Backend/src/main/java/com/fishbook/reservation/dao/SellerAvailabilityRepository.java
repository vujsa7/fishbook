package com.fishbook.reservation.dao;

import com.fishbook.reservation.model.SellerAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;

public interface SellerAvailabilityRepository extends JpaRepository<SellerAvailability, Long> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value="0")})
    List<SellerAvailability> findAllBySellerId(Long sellerId);
}
