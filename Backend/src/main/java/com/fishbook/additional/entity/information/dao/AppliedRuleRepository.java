package com.fishbook.additional.entity.information.dao;

import com.fishbook.additional.entity.information.model.AppliedRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppliedRuleRepository extends JpaRepository<AppliedRule, Long> {
    List<AppliedRule> findAllByType(String type);
}
