package com.fishbook.system.service;

import com.fishbook.system.model.GlobalConfig;
import com.fishbook.system.model.LoyaltyConfig;

import java.util.Optional;

public interface ConfigService {
    void update(LoyaltyConfig loyaltyConfig);
    void update(GlobalConfig globalConfig);
    Optional<LoyaltyConfig> findLoyaltyConfigById(Integer id);
    Optional<GlobalConfig> findGlobalConfigById(Integer id);
}
