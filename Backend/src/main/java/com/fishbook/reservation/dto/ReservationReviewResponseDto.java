package com.fishbook.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReservationReviewResponseDto {

    private Long reservationReviewId;
    private String response;
    private Boolean approved;
}
