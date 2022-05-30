package com.fishbook.reservation.dto;

import com.fishbook.additional.entity.information.model.AdditionalService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@AllArgsConstructor
@ToString
public class SpecialOfferDto {
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Integer maxNumberOfPeople;
    private Set<AdditionalService> additionalServices;
    private Long entityId;
    private Double discount;

}
