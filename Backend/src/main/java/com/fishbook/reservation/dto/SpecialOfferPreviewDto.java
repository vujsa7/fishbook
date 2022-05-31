package com.fishbook.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SpecialOfferPreviewDto {
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private Double oldPrice;
    private Double newPrice;
}
