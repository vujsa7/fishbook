package com.fishbook.additional.entity.information.service.impl;

import com.fishbook.additional.entity.information.dao.RuleRepository;
import com.fishbook.additional.entity.information.model.Rule;
import com.fishbook.additional.entity.information.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleServiceImpl implements RuleService {
    private final RuleRepository ruleRepository;

    @Autowired
    public RuleServiceImpl(RuleRepository appliedRuleRepository) {
        this.ruleRepository = appliedRuleRepository;
    }

    @Override
    public List<Rule> getRules(String type) {
        return ruleRepository.findAllByType(type);
    }
}
