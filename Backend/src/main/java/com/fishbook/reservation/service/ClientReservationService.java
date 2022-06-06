package com.fishbook.reservation.service;

import com.fishbook.entity.model.Entity;
import com.fishbook.exception.ApiRequestException;
import com.fishbook.reservation.dto.ClientReservationDto;
import com.fishbook.reservation.model.Reservation;
import com.fishbook.reservation.model.ReservationCandidate;

import java.time.LocalDateTime;
import java.util.List;

public interface ClientReservationService {
    Entity getReservationOfferDetails(Long entityId);
    ReservationCandidate createReservation(ReservationCandidate reservationCandidate) throws ApiRequestException;
    List<Reservation> getReservationHistory(String name);
    String getStatus(Reservation reservation);
    void cancelReservation(Long reservationId, String email) throws ApiRequestException;
    void reviewReservation(Integer rating, String comment, String name, Long reservationId);
    void createReservationOnSpecialOffer(Long specialOfferId, String email);
}
