package com.fishbook.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@ToString
public class CalculateRevenueDto {

    private LocalDateTime fromDateTime;
    private LocalDateTime toDateTime;
    private Long entityId;
}
