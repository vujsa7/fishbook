package com.fishbook.reservation.dto;

import com.fishbook.additional.entity.information.model.AdditionalService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SellerReservationRequestDto {

    private Long entityId;
    private Long clientId;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private List<AdditionalService> additionalServices;
    private Integer maxNumberOfPeople;
}
