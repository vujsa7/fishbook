package com.fishbook.system.service;

import com.fishbook.system.model.GlobalConfig;
import com.fishbook.system.model.LoyaltyConfig;

import java.util.List;
import java.util.Optional;

public interface ConfigService {
    List<LoyaltyConfig> getLoyaltyConfig();
    GlobalConfig getGlobalConfig();
    void update(LoyaltyConfig loyaltyConfig);
    void update(GlobalConfig globalConfig);
    Optional<LoyaltyConfig> findLoyaltyConfigById(Integer id);
    Optional<GlobalConfig> findGlobalConfigById(Integer id);
    List<Integer> getClientLevelMarks();
    List<Integer> getSellerLevelMarks();
    Integer getClientLoyaltyPointsForNextLevel(Integer points);
}
