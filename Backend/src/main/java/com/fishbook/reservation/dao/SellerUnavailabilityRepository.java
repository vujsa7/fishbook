package com.fishbook.reservation.dao;

import com.fishbook.reservation.model.SellerUnavailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;

public interface SellerUnavailabilityRepository extends JpaRepository<SellerUnavailability, Long> {

    List<SellerUnavailability> findAllBySellerId(Long sellerId);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value="0")})
    @Query("SELECT s FROM SellerUnavailability s WHERE s.seller.id = :sellerId")
    List<SellerUnavailability> findSellerUnavailability(@Param("sellerId") Long sellerId);

}
