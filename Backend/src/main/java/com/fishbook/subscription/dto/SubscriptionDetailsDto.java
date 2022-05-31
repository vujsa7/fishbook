package com.fishbook.subscription.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubscriptionDetailsDto {
    private String imageUrl;
    private String name;
    private String type;
    private Integer activeSpecialOffers;
    private Long entityId;
}
