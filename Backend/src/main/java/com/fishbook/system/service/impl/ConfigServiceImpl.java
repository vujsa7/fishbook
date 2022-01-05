package com.fishbook.system.service.impl;

import com.fishbook.system.dao.GlobalConfigRepository;
import com.fishbook.system.dao.LoyaltyConfigRepository;
import com.fishbook.system.model.GlobalConfig;
import com.fishbook.system.model.LoyaltyConfig;
import com.fishbook.system.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConfigServiceImpl implements ConfigService {
    private final LoyaltyConfigRepository loyaltyConfigRepository;
    private final GlobalConfigRepository globalConfigRepository;

    @Autowired
    public ConfigServiceImpl(LoyaltyConfigRepository loyaltyConfigRepository, GlobalConfigRepository globalConfigRepository){
        this.loyaltyConfigRepository = loyaltyConfigRepository;
        this.globalConfigRepository = globalConfigRepository;
    }

    @Override
    public List<LoyaltyConfig> getLoyaltyConfig() {
        return loyaltyConfigRepository.findAll();
    }

    @Override
    public GlobalConfig getGlobalConfig() {
        return globalConfigRepository.findAll().stream().findFirst().orElse(new GlobalConfig());
    }

    @Override
    public void update(LoyaltyConfig loyaltyConfig) {
        loyaltyConfigRepository.save(loyaltyConfig);
    }

    @Override
    public void update(GlobalConfig globalConfig) {
        globalConfigRepository.save(globalConfig);
    }

    @Override
    public Optional<LoyaltyConfig> findLoyaltyConfigById(Integer id) {
        return loyaltyConfigRepository.findById(id);
    }

    @Override
    public Optional<GlobalConfig> findGlobalConfigById(Integer id) {
        return globalConfigRepository.findById(id);
    }
}
