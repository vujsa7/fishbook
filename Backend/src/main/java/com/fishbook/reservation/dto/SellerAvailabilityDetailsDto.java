package com.fishbook.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SellerAvailabilityDetailsDto {

    private Long id;
    private LocalDateTime fromDateTime;
    private LocalDateTime toDateTime;
    private Long version;
    private Long sellerId;
}
