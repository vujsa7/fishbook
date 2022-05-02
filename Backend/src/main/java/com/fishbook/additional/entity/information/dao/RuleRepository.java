package com.fishbook.additional.entity.information.dao;

import com.fishbook.additional.entity.information.model.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RuleRepository extends JpaRepository<Rule, Long> {
    List<Rule> findAllByType(String type);
}
