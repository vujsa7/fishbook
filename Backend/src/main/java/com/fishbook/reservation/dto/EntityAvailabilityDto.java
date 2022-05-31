package com.fishbook.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EntityAvailabilityDto {

    private LocalDateTime fromDateTime;
    private LocalDateTime toDateTime;
    private Long entityId;
}
