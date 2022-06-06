package com.fishbook.reports.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateReportDto {

    @NotNull
    private Long reservationId;

    @NotBlank
    private String comment;

    @NotNull
    private Boolean clientArrived;

    @NotNull
    private Boolean shouldGetPenalty;
}
