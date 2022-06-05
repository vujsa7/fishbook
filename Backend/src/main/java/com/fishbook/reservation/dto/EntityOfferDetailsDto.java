package com.fishbook.reservation.dto;

import com.fishbook.additional.entity.information.model.AdditionalService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EntityOfferDetailsDto {
    private Long entityId;
    private String entityName;
    private Double pricePerDay;
    private Set<AdditionalService> additionalServices;
    private List<DateRangeDto> availableDates;
}
