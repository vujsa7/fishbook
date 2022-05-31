package com.fishbook.boat.dto;

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
public class BoatDetailsDto {
    private Long id;
    private List<String> images;
    private String name;
    private String seller;
    private String description;
    private Double rating;
    private Double price;
    private Double cancellationFee;
    private LocationDto location;
    private BoatSpecificationsDto boatSpecifications;
    private String sellerEmail;
    private List<String> rules;
    private List<String> navigationEquipment;
    private List<String> fishingEquipment;
    private List<AdditionalService> additionalServices;
    private List<SpecialOfferPreviewDto> specialOffers;
}
