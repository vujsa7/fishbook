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
public class SellerAvailabilityDto {

    private LocalDateTime fromDateTime;
    private LocalDateTime toDateTime;
}
