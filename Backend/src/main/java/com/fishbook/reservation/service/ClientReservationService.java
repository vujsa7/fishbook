package com.fishbook.reservation.service;

import com.fishbook.entity.model.Entity;
import com.fishbook.exception.ApiRequestException;
import com.fishbook.reservation.model.ReservationCandidate;

public interface ClientReservationService {
    Entity getReservationOfferDetails(Long entityId);
    ReservationCandidate createReservation(ReservationCandidate reservationCandidate) throws ApiRequestException;
}
