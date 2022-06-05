package com.fishbook.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientReservationDto {
    private Long id;
    private String imageUrl;
    private String name;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private Double price;
    private String status;
    private Long entityId;
}
