package com.fishbook.fishing.lesson.controller;

import com.fishbook.additional.entity.information.model.Equipment;
import com.fishbook.additional.entity.information.model.Rule;
import com.fishbook.additional.entity.information.service.EquipmentService;
import com.fishbook.additional.entity.information.service.RuleService;
import com.fishbook.fishing.lesson.dto.FishingLessonCreatedDto;
import com.fishbook.fishing.lesson.dto.FishingLessonRegistrationDto;
import com.fishbook.fishing.lesson.model.FishingLesson;
import com.fishbook.fishing.lesson.service.FishingLessonService;
import com.fishbook.user.model.User;
import com.fishbook.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/fishingLessons")
public class FishingLessonController {
    private final RuleService ruleService;
    private final UserService userService;
    private final EquipmentService equipmentService;
    private final FishingLessonService fishingLessonService;

    @Autowired
    public FishingLessonController(RuleService ruleService, UserService userService, EquipmentService equipmentService, FishingLessonService fishingLessonService) {
        this.ruleService = ruleService;
        this.userService = userService;
        this.equipmentService = equipmentService;
        this.fishingLessonService = fishingLessonService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
    public ResponseEntity createFishingLesson(@RequestBody FishingLessonRegistrationDto dto, Principal principal){
        User instructor = userService.findByEmail(principal.getName());
        FishingLesson fishingLesson = new FishingLesson(dto.getName(), dto.getDescription(), dto.getCancellationFee(), dto.getPricePerDay(),
                false, dto.getAddress(), dto.getRules(), dto.getAdditionalServices(), instructor, dto.getInstructorBiography(),
                dto.getMaxNumberOfPeople(), dto.getEquipment());
        FishingLesson createdFishingLesson = fishingLessonService.save(fishingLesson);

        return new ResponseEntity<>(new FishingLessonCreatedDto(createdFishingLesson.getId()), HttpStatus.CREATED);
    }

    @GetMapping(value = "/rules")
    @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
    public List<Rule> getRules(){
        return ruleService.getRules("fishingLesson");
    }

    @GetMapping(value = "/equipment")
    @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
    public List<Equipment> getBoatEquipment(){
        return equipmentService.getFishingEquipment();
    }
}
