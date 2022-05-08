package com.fishbook.fishing.lesson.controller;

import com.fishbook.additional.entity.information.model.Rule;
import com.fishbook.additional.entity.information.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/fishingLessons")
public class FishingLessonController {
    private final RuleService ruleService;

    @Autowired
    public FishingLessonController(RuleService ruleService) {
        this.ruleService = ruleService;
    }

    @GetMapping(value = "/rules")
    @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
    public List<Rule> getRules(){
        return ruleService.getRules("fishingLesson");
    }
}
