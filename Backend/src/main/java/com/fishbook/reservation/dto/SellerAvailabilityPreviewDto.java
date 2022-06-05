package com.fishbook.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SellerAvailabilityPreviewDto {
    private String email;
    private List<DateRangeDto> availability;
}
