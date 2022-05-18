package com.fishbook.fishing.lesson.dto;

import com.fishbook.additional.entity.information.model.AdditionalService;
import com.fishbook.additional.entity.information.model.Equipment;
import com.fishbook.additional.entity.information.model.Rule;
import com.fishbook.location.model.Address;
import com.fishbook.storage.model.EntityImage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public class FishingLessonUpdateDto {
    private Long id;
    private String name;
    private String description;
    private Address address;
    private Double cancellationFee;
    private Double pricePerDay;
    private Integer maxNumberOfPeople;
    private String instructorBiography;
    private Set<Rule> rules;
    private Set<Equipment> equipment;
    private Set<AdditionalService> additionalServices;
    private Set<EntityImage> images;
}
