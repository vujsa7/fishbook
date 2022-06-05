package com.fishbook.system.service.impl;

import com.fishbook.entity.dao.EntityRepository;
import com.fishbook.entity.model.Entity;
import com.fishbook.system.dao.GlobalConfigRepository;
import com.fishbook.system.dao.LoyaltyConfigRepository;
import com.fishbook.system.model.GlobalConfig;
import com.fishbook.system.model.LoyaltyConfig;
import com.fishbook.system.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConfigServiceImpl implements ConfigService {
    private final LoyaltyConfigRepository loyaltyConfigRepository;
    private final GlobalConfigRepository globalConfigRepository;
    private final EntityRepository entityRepository;

    @Autowired
    public ConfigServiceImpl(LoyaltyConfigRepository loyaltyConfigRepository, GlobalConfigRepository globalConfigRepository, EntityRepository entityRepository){
        this.loyaltyConfigRepository = loyaltyConfigRepository;
        this.globalConfigRepository = globalConfigRepository;
        this.entityRepository = entityRepository;
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

    @Override
    public List<Integer> getClientLevelMarks() {
        List<Integer> levelMarks = loyaltyConfigRepository.findAll().stream().map(l -> l.getBuyerMinPoints()).collect(Collectors.toList());
        return levelMarks;
    }

    @Override
    public List<Double> getClientDiscounts() {
        List<Double> discounts = loyaltyConfigRepository.findAll().stream().map(l -> l.getDiscount()).collect(Collectors.toList());
        return discounts;
    }

    @Override
    public List<Integer> getSellerLevelMarks() {
        List<Integer> levelMarks = loyaltyConfigRepository.findAll().stream().map(l -> l.getSellerMinPoints()).collect(Collectors.toList());
        return levelMarks;
    }

    @Override
    public Integer getClientLoyaltyPointsForNextLevel(Integer points) {
        List<Integer> levelMarks = this.getClientLevelMarks();
        for(Integer mark : levelMarks){
            if(points < mark){
                return mark;
            }
        }
        return levelMarks.get(levelMarks.size()-1);
    }

    @Override
    public Double getClientDiscountPercentageForPoints(Integer points) {
        Double discount = 0.0;
        List<Double> discounts = getClientDiscounts();
        int cnt = 0;
        for(Integer levelMark : getClientLevelMarks()){
            if(points >= levelMark){
                discount = discounts.get(cnt);
            } else
                break;
        }
        return discount;
    }

    @Override
    public Double getSellerExtraRevenueForPoints(Integer points) {
        Double extraRevenue = 0.0;
        List<Double> extraRevenues = getSellerExtraRevenues();
        int cnt = 0;
        for(Integer levelMark : getSellerLevelMarks()){
            if(points >= levelMark){
                extraRevenue = extraRevenues.get(cnt);
            } else
                break;
        }
        return extraRevenue;
    }

    @Override
    public Double getSellerExtraRevenueForEntity(Long entityId) {
        Optional<Entity> optionalEntity = entityRepository.findById(entityId);
        if(optionalEntity.isEmpty())
            return null;
        Entity entity = optionalEntity.get();
        Double sellerExtraRevenue = getSellerExtraRevenueForPoints(entity.getOwner().getPoints());
        return sellerExtraRevenue;
    }

    private List<Double> getSellerExtraRevenues() {
        List<Double> extraRevenues = loyaltyConfigRepository.findAll().stream().map(l -> l.getExtraRevenue()).collect(Collectors.toList());
        return extraRevenues;
    }
}
