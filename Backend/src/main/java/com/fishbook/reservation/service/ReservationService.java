package com.fishbook.reservation.service;

import com.fishbook.reservation.model.Reservation;

public interface ReservationService {
    void createReservation(Reservation reservation, Long clientId, Long entityId) throws InterruptedException;
}

