package com.fishbook.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReservationReviewDetailsDto {

    private Long id;
    private Integer rating;
    private String comment;
    private Long reservationId;
    private String sellerEmail;
    private String sellerPhoneNumber;
    private String clientEmail;
    private String clientPhoneNumber;
    private Boolean approved;
}
