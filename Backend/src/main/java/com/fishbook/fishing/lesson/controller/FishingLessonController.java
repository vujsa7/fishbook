package com.fishbook.fishing.lesson.controller;

import com.fishbook.additional.entity.information.service.EquipmentService;
import com.fishbook.fishing.lesson.dto.FishingLessonCreatedDto;
import com.fishbook.entity.dto.EntityBasicInfoDto;
import com.fishbook.fishing.lesson.dto.FishingLessonDetailsDto;
import com.fishbook.fishing.lesson.dto.FishingLessonRegistrationDto;
import com.fishbook.fishing.lesson.dto.FishingLessonUpdateDto;
import com.fishbook.fishing.lesson.model.FishingLesson;
import com.fishbook.fishing.lesson.service.FishingLessonService;
import com.fishbook.location.dto.LocationDto;
import com.fishbook.storage.service.StorageService;
import com.fishbook.user.model.User;
import com.fishbook.user.service.UserService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class FishingLessonController {
    private final UserService userService;
    private final FishingLessonService fishingLessonService;
    private final StorageService storageService;

    @PostMapping
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity createFishingLesson(@RequestBody FishingLessonRegistrationDto dto, Principal principal){
        User instructor = userService.findByEmail(principal.getName());
        FishingLesson fishingLesson = new FishingLesson(dto.getName(), dto.getDescription(), dto.getCancellationFee(), dto.getPricePerDay(),
                false, instructor, dto.getAddress(), dto.getRules(), dto.getAdditionalServices(), dto.getInstructorBiography(),
                dto.getMaxNumberOfPeople(), dto.getEquipment());
        FishingLesson createdFishingLesson = fishingLessonService.save(fishingLesson);

        return new ResponseEntity<>(new FishingLessonCreatedDto(createdFishingLesson.getId()), HttpStatus.CREATED);
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
                fishingLesson.getInstructorBiography(), fishingLesson.getOwner().getEmail(), fishingLesson.getRules().stream().map(rule -> rule.getDescription()).collect(Collectors.toList()),
                fishingLesson.getFishingEquipment().stream().map(equipment -> equipment.getName()).collect(Collectors.toList()), new ArrayList<>()), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR')")
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

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity updateFishingLesson(@PathVariable Long id, @RequestBody FishingLessonUpdateDto dto, Authentication authentication){
        Optional<FishingLesson> fishingLesson = fishingLessonService.findById(id);
        if(fishingLesson.isEmpty() || !Objects.equals(fishingLesson.get().getId(), dto.getId())){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User userDetails = (User) authentication.getPrincipal();
        if(!Objects.equals(fishingLesson.get().getOwner().getId(), userDetails.getId())){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        FishingLesson updatedFishingLesson = update(fishingLesson.get(), dto);

        fishingLessonService.save(updatedFishingLesson);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private FishingLesson update(FishingLesson fishingLesson, FishingLessonUpdateDto dto){
        fishingLesson.setName(dto.getName());
        fishingLesson.setDescription(dto.getDescription());
        fishingLesson.setCancellationFee(dto.getCancellationFee());
        fishingLesson.setPricePerDay(dto.getPricePerDay());
        fishingLesson.setAddress(dto.getAddress());
        fishingLesson.setRules(dto.getRules());
        fishingLesson.setAdditionalServices(dto.getAdditionalServices());
        fishingLesson.setInstructorBiography(dto.getInstructorBiography());
        fishingLesson.setMaxNumberOfPeople(dto.getMaxNumberOfPeople());
        fishingLesson.setFishingEquipment(dto.getEquipment());

        return fishingLesson;
    }
}
