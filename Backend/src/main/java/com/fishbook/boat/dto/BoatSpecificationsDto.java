package com.fishbook.boat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoatSpecificationsDto {
    private String boatType;
    private Integer maxPeople;
    private Double length;
    private Integer loadCapacity;
    private Integer maximumSpeed;
    private Integer horsepower;
    private Integer motorsOnBoat;
    private Integer fuelConsumption;
    private Integer maxDistanceOnTank;
    private Integer energyConsumption;
}
