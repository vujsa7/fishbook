package com.fishbook.reservation.service;

import com.fishbook.reservation.model.ReservationReview;

import java.util.List;

public interface ReservationReviewService {

    List<ReservationReview> getAll();
    void respond(Long reviewId, String response, Boolean approved) throws InterruptedException;
}
