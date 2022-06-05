package com.fishbook.reservation.dao;

import com.fishbook.reservation.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByClientId(Long id);
}
