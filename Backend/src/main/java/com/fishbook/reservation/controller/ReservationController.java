package com.fishbook.reservation.controller;

import com.fishbook.reservation.dto.ReservationDto;
import com.fishbook.reservation.model.Reservation;
import com.fishbook.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    @PreAuthorize("hasAnyRole('BOAT_OWNER', 'HOUSE_OWNER', 'INSTRUCTOR', 'CLIENT')")
    public ResponseEntity<List<ReservationDto>> getAll(@RequestParam(value = "entityId") Long entityId) {
        List<ReservationDto> reservations = reservationService.getAll(entityId).stream()
                .map(reservation -> new ReservationDto(reservation.getId(), reservation.getStartDateTime(), reservation.getEndDateTime(),
                        reservation.getMaxNumberOfPeople(), reservation.getAdditionalServices(), reservation.getEntity().getId(), reservation.getPrice(),
                        reservation.getClient().getId(), reservation.getClient().getFirstName(), reservation.getClient().getLastName(), reservation.getClient().getEmail(),
                        reservation.getClient().getPhoneNumber()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('BOAT_OWNER', 'HOUSE_OWNER', 'INSTRUCTOR', 'CLIENT')")
    public ResponseEntity<ReservationDto> get(@PathVariable Long id) {
        Reservation reservation = reservationService.get(id);
        ReservationDto reservationDto = new ReservationDto(reservation.getId(), reservation.getStartDateTime(), reservation.getEndDateTime(),
                reservation.getMaxNumberOfPeople(), reservation.getAdditionalServices(), reservation.getEntity().getId(), reservation.getPrice(),
                reservation.getClient().getId(), reservation.getClient().getFirstName(), reservation.getClient().getLastName(), reservation.getClient().getEmail(),
                reservation.getClient().getPhoneNumber());

        return new ResponseEntity<>(reservationDto, HttpStatus.OK);
    }
}
