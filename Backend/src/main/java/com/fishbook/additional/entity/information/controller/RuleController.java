package com.fishbook.additional.entity.information.controller;

import com.fishbook.additional.entity.information.model.Rule;
import com.fishbook.additional.entity.information.service.RuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/rules")
@RequiredArgsConstructor
public class RuleController {
    private final RuleService ruleService;

    @GetMapping
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'BOAT_OWNER', 'HOUSE_OWNER')")
    public List<Rule> getRules(@RequestParam String type){
        return ruleService.getRules(type);
    }
}
