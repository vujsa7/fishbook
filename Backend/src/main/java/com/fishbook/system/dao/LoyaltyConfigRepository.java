package com.fishbook.system.dao;

import com.fishbook.system.model.LoyaltyConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoyaltyConfigRepository extends JpaRepository<LoyaltyConfig, Integer> {
}
