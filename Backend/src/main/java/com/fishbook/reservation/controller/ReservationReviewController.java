package com.fishbook.reservation.controller;

import com.fishbook.reservation.dto.ReservationReviewDetailsDto;
import com.fishbook.reservation.dto.ReservationReviewResponseDto;
import com.fishbook.reservation.service.ReservationReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/reviews")
@RequiredArgsConstructor
public class ReservationReviewController {

    private final ReservationReviewService reservationReviewService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAll() {
        List<ReservationReviewDetailsDto> reviews = reservationReviewService.getAll().stream()
                .map(review -> new ReservationReviewDetailsDto(review.getId(), review.getRating(), review.getComment(), review.getReservation().getId(),
                        review.getReservation().getEntity().getOwner().getEmail(), review.getReservation().getEntity().getOwner().getPhoneNumber(),
                        review.getReservation().getClient().getEmail(), review.getReservation().getClient().getPhoneNumber(), review.getApproved()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity respondToBuyerReport(@RequestBody ReservationReviewResponseDto dto) throws InterruptedException {
        reservationReviewService.respond(dto.getReservationReviewId(), dto.getResponse(), dto.getApproved());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
