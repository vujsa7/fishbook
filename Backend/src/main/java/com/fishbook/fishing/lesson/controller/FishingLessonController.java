package com.fishbook.fishing.lesson.controller;

import com.fishbook.additional.entity.information.model.Equipment;
import com.fishbook.additional.entity.information.model.Rule;
import com.fishbook.additional.entity.information.service.EquipmentService;
import com.fishbook.additional.entity.information.service.RuleService;
import com.fishbook.fishing.lesson.dto.FishingLessonCreatedDto;
import com.fishbook.fishing.lesson.dto.FishingLessonDto;
import com.fishbook.fishing.lesson.dto.FishingLessonRegistrationDto;
import com.fishbook.fishing.lesson.model.FishingLesson;
import com.fishbook.fishing.lesson.service.FishingLessonService;
import com.fishbook.user.model.User;
import com.fishbook.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
                false, instructor, dto.getAddress(), dto.getRules(), dto.getAdditionalServices(), dto.getInstructorBiography(),
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

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity getAllFishingLessons(){
        List<FishingLessonDto> lessons = fishingLessonService.getAll().stream()
                .map(fishingLesson -> new FishingLessonDto(fishingLesson.getId(), fishingLesson.getName(), fishingLesson.getDescription(), fishingLesson.getCancellationFee(),
                    fishingLesson.getPricePerDay(), fishingLesson.getAddress().getCity().getCountry().getName(), fishingLesson.getAddress().getCity().getName(), fishingLesson.getAddress().getAddress(),
                    fishingLesson.getRules(), fishingLesson.getAdditionalServices(), fishingLesson.getImages(), fishingLesson.getOwner().getFirstName(), fishingLesson.getOwner().getLastName(),
                    fishingLesson.getInstructorBiography(), fishingLesson.getMaxNumberOfPeople(), fishingLesson.getFishingEquipment()))
                .collect(Collectors.toList());

        return new ResponseEntity(lessons, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_INSTRUCTOR')")
    public ResponseEntity deleteFishingLesson(@PathVariable Long id, Authentication authentication){
        Optional<FishingLesson> fishingLesson = fishingLessonService.findById(id);
        if(fishingLesson.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        boolean isInstructor = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().matches("ROLE_INSTRUCTOR"));
        if(isInstructor){
            User userDetails = (User) authentication.getPrincipal();
            if(!Objects.equals(fishingLesson.get().getOwner().getId(), userDetails.getId())){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }

        fishingLessonService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
