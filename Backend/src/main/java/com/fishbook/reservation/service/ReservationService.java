package com.fishbook.reservation.service;

import com.fishbook.entity.model.Entity;
import com.fishbook.reservation.model.Reservation;

import java.util.List;

public interface ReservationService {

    Integer getNumberOfReservations(Entity entity);
    List<Reservation> getAll(Long entityId);
    Reservation get(Long id);
}
