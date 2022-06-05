package com.fishbook.reports.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateReportDto {

    private Long reservationId;
    private String comment;
    private Boolean clientArrived;
    private Boolean shouldGetPenalty;
}
