package com.fishbook.house.dto;

import com.fishbook.additional.entity.information.model.AdditionalService;
import com.fishbook.house.model.Room;
import com.fishbook.location.model.Address;
import com.fishbook.storage.model.EntityImage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor
public class HouseUpdateDto {
    private Long id;
    private String name;
    private String description;
    private Address address;
    private Integer maxPeople;
    private List<Room> rooms;
    private Double price;
    private Double cancellationFee;
    private Set<AdditionalService> additionalServices;
    private Set<EntityImage> images;
}
