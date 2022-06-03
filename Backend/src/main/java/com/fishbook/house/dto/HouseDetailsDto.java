package com.fishbook.house.dto;

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
public class HouseDetailsDto {
    private Long id;
    private List<String> images;
    private String name;
    private String seller;
    private String description;
    private Double rating;
    private Double price;
    private Double cancellationFee;
    private LocationDto location;
    private HouseSpecificationsDto houseSpecifications;
    private String sellerEmail;
    private Long sellerId;
    private List<String> rules;
    private List<AdditionalService> additionalServices;
    private List<SpecialOfferPreviewDto> specialOffers;
}
