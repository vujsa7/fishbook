package com.fishbook.system.service;

public interface LoyaltyPointsService {

    void addLoyaltyPoints();
    Double getLoyaltyDiscount(Long userId);
    Double getLoyaltyRevenue(Long userId);
}
