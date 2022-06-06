package com.fishbook.reports.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BuyerReportDetailsDto {

    private Long id;
    private String comment;
    private Long reservationId;
    private String sellerEmail;
    private String sellerPhoneNumber;
    private String clientEmail;
    private String clientPhoneNumber;
}
