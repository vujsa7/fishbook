package com.fishbook.reports.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdminResponseOnSellerReportDto {
    private Long reportId;
    private String sellerMessage;
    private String buyerMessage;
    private Boolean givePenalty;
    private String sellerEmail;
    private String buyerEmail;
    private Boolean buyerArrived;
}
