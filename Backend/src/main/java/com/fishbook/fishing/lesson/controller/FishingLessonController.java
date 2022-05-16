package com.fishbook.fishing.lesson.controller;

import com.fishbook.additional.entity.information.model.Equipment;
import com.fishbook.additional.entity.information.model.Rule;
import com.fishbook.additional.entity.information.service.EquipmentService;
import com.fishbook.additional.entity.information.service.RuleService;
import com.fishbook.fishing.lesson.dto.FishingLessonCreatedDto;
import com.fishbook.entity.dto.EntityBasicInfoDto;
import com.fishbook.fishing.lesson.dto.FishingLessonDetailsDto;
import com.fishbook.fishing.lesson.dto.FishingLessonRegistrationDto;
import com.fishbook.fishing.lesson.model.FishingLesson;
import com.fishbook.fishing.lesson.service.FishingLessonService;
import com.fishbook.house.dto.HouseDetailsDto;
import com.fishbook.house.dto.HouseSpecificationsDto;
import com.fishbook.house.model.House;
import com.fishbook.location.dto.LocationDto;
import com.fishbook.storage.service.StorageService;
import com.fishbook.user.model.User;
import com.fishbook.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
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
    private final StorageService storageService;

    @Autowired
    public FishingLessonController(RuleService ruleService, UserService userService, EquipmentService equipmentService, FishingLessonService fishingLessonService, StorageService storageService) {
        this.ruleService = ruleService;
        this.userService = userService;
        this.equipmentService = equipmentService;
        this.fishingLessonService = fishingLessonService;
        this.storageService = storageService;
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
    public ResponseEntity getAllFishingLessons(){
        List<EntityBasicInfoDto> lessons = fishingLessonService.getAll().stream()
                .map(fishingLesson -> new EntityBasicInfoDto(fishingLesson.getId(), storageService.getPriorityImageUrl(fishingLesson.getImages()), fishingLesson.getName(), fishingLesson.getDescription(),
                    fishingLesson.getPricePerDay(), fishingLesson.getAddress().getCity().getName(), fishingLesson.getAddress().getCity().getCountry().getName(),
                    fishingLesson.getOwner().getFirstName() + " " + fishingLesson.getOwner().getLastName()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(lessons, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getFishingLessonDetails(@PathVariable Long id){
        Optional<FishingLesson> fishingLessonOptional = fishingLessonService.findById(id);
        if(fishingLessonOptional.isEmpty()){
            return new ResponseEntity("Adventure with that id doesn't exist", HttpStatus.NOT_FOUND);
        }
        FishingLesson fishingLesson = fishingLessonOptional.get();
        return new ResponseEntity(new FishingLessonDetailsDto(fishingLesson.getId(), storageService.getImageUrls(fishingLesson.getImages()), fishingLesson.getName(), fishingLesson.getOwner().getFullName(),
                fishingLesson.getDescription(), 0.0, fishingLesson.getPricePerDay(), fishingLesson.getCancellationFee(), new LocationDto(fishingLesson.getAddress().getAddress(), fishingLesson.getAddress().getCity().getName(),
                fishingLesson.getAddress().getCity().getCountry().getName(), fishingLesson.getAddress().getLongitude(), fishingLesson.getAddress().getLatitude()), fishingLesson.getMaxNumberOfPeople(), "https://images.unsplash.com/photo-1463062511209-f7aa591fa72f?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                fishingLesson.getInstructorBiography(), fishingLesson.getRules().stream().map(rule -> rule.getDescription()).collect(Collectors.toList()),
                fishingLesson.getFishingEquipment().stream().map(equipment -> equipment.getName()).collect(Collectors.toList()), new ArrayList<>()), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_INSTRUCTOR')")
    public ResponseEntity deleteFishingLesson(@PathVariable Long id, Authentication authentication){
        Optional<FishingLesson> fishingLesson = fishingLessonService.findById(id);
        if(fishingLesson.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User userDetails = (User) authentication.getPrincipal();
        if(Objects.equals(userDetails.getRole().getName(), "ROLE_INSTRUCTOR") && !Objects.equals(fishingLesson.get().getOwner().getId(), userDetails.getId())){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        fishingLessonService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
