package com.fishbook.reservation.dao;

import com.fishbook.reservation.model.SellerUnavailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;

public interface SellerUnavailabilityRepository extends JpaRepository<SellerUnavailability, Long> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value="0")})
    List<SellerUnavailability> findAllBySellerId(Long sellerId);
}
