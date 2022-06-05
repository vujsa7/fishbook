package com.fishbook.reservation.dao;

import com.fishbook.reservation.model.SellerAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.time.LocalDateTime;
import java.util.List;

public interface SellerAvailabilityRepository extends JpaRepository<SellerAvailability, Long> {

    List<SellerAvailability> findAllBySellerId(Long sellerId);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value="0")})
    @Query("SELECT s FROM SellerAvailability s WHERE s.seller.id = :sellerId")
    List<SellerAvailability> findSellerAvailabilities(@Param("sellerId") Long sellerId);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value="0")})
    @Query("SELECT s FROM SellerAvailability s WHERE s.seller.id = :sellerId and (s.fromDateTime = :reservationEnd or s.toDateTime = :reservationStart)")
    List<SellerAvailability> findSellerAvailabilitiesToMerge(@Param("reservationStart") LocalDateTime reservationStart, @Param("reservationEnd") LocalDateTime reservationEnd, @Param("sellerId") Long sellerId);
}
