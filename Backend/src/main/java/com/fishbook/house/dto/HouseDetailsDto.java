package com.fishbook.house.dto;

import com.fishbook.location.dto.LocationDto;
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
    private List<String> rules;
}
