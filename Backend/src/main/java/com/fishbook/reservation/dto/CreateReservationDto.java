package com.fishbook.reservation.dto;

import com.fishbook.additional.entity.information.model.AdditionalService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateReservationDto {
    private LocalDateTime start;
    private LocalDateTime end;
    private Double totalPrice;
    private List<AdditionalService> additionalServices;
    private Long entityId;
}
