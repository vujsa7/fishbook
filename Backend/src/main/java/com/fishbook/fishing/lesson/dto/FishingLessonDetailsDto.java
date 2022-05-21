package com.fishbook.fishing.lesson.dto;

import com.fishbook.location.dto.LocationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FishingLessonDetailsDto {
    private Long id;
    private List<String> images;
    private String name;
    private String seller;
    private String description;
    private Double rating;
    private Double price;
    private Double cancellationFee;
    private LocationDto location;
    private Integer maxPeople;
    private String sellerImg;
    private String aboutSeller;
    private String sellerEmail;
    private List<String> rules;
    private List<String> fishingEquipment;
    private List<String> previousAdventureImages;
}
