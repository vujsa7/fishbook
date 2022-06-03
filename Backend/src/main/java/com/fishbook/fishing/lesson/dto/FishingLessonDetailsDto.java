package com.fishbook.fishing.lesson.dto;

import com.fishbook.additional.entity.information.model.AdditionalService;
import com.fishbook.location.dto.LocationDto;
import com.fishbook.reservation.dto.SpecialOfferPreviewDto;
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
    private Long sellerId;
    private List<String> rules;
    private List<String> fishingEquipment;
    private List<String> previousAdventureImages;
    private List<AdditionalService> additionalServices;
    private List<SpecialOfferPreviewDto> specialOffers;
}
