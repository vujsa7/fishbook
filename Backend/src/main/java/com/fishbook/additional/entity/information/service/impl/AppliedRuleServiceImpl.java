package com.fishbook.additional.entity.information.service.impl;

import com.fishbook.additional.entity.information.dao.AppliedRuleRepository;
import com.fishbook.additional.entity.information.model.AppliedRule;
import com.fishbook.additional.entity.information.service.AppliedRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppliedRuleServiceImpl implements AppliedRuleService {
    private final AppliedRuleRepository appliedRuleRepository;

    @Autowired
    public AppliedRuleServiceImpl(AppliedRuleRepository appliedRuleRepository) {
        this.appliedRuleRepository = appliedRuleRepository;
    }

    @Override
    public List<AppliedRule> getBoatRules() {
        return appliedRuleRepository.findAllByType("boat");
    }
}
