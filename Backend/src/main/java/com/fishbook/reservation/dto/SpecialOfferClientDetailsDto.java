package com.fishbook.reservation.dto;

import com.fishbook.additional.entity.information.model.AdditionalService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SpecialOfferClientDetailsDto {
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private Set<AdditionalService> additionalServices;
    private Long entityId;
    private String entityName;
    private Double price;
}
