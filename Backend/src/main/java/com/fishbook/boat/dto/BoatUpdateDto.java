package com.fishbook.boat.dto;

import com.fishbook.additional.entity.information.model.AdditionalService;
import com.fishbook.additional.entity.information.model.Equipment;
import com.fishbook.location.model.Address;
import com.fishbook.storage.model.EntityImage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public class BoatUpdateDto {
    private Long id;
    private String name;
    private String description;
    private Address address;
    private String boatType;
    private Integer maxPeople;
    private Double length;
    private Integer loadCapacity;
    private Integer maxSpeed;
    private Integer power;
    private Integer motors;
    private Integer fuelConsumption;
    private Integer maxDistance;
    private Integer energyConsumption;
    private Set<Equipment> equipment;
    private Double price;
    private Double cancellationFee;
    private Set<AdditionalService> additionalServices;
    private Set<EntityImage> images;
}
