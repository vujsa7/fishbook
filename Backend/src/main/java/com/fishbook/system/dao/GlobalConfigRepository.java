package com.fishbook.system.dao;

import com.fishbook.system.model.GlobalConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GlobalConfigRepository extends JpaRepository<GlobalConfig, Integer> {
}
