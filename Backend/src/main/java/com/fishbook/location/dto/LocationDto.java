package com.fishbook.location.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto {
    private String address;
    private String city;
    private String country;
    private Double lon;
    private Double lat;
}
