package com.fishbook.reports.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SellerReportPreviewDto {
    private Long id;
    private String sellerEmail;
    private String buyerEmail;
    private Boolean buyerArrived;
    private Boolean shouldGetPenalty;
    private String comment;
}
