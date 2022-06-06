package com.fishbook.reservation.dao;

import com.fishbook.reservation.model.ReservationReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationReviewRepository extends JpaRepository<ReservationReview, Long> {

    Optional<ReservationReview> findOneByReservationId(Long reservationId);
}
