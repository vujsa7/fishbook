package com.fishbook.reservation.dao;

import com.fishbook.reservation.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
