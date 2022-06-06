package com.fishbook.reservation.dao;

import com.fishbook.reservation.model.ReservationReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReservationReviewRepository extends JpaRepository<ReservationReview, Long> {

    Optional<ReservationReview> findOneByReservationId(Long reservationId);

    @Query("select r from ReservationReview r where r.reservation.entity.id = :entityId and r.approved = true")
    List<ReservationReview> getAllApprovedReviews(Long entityId);
}
