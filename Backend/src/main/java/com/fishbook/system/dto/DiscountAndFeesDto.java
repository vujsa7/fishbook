package com.fishbook.system.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DiscountAndFeesDto {
    private Double discount;
    private Double sellerFee;
    private Double systemFee;
}
