package com.fishbook.boat.controller;

import com.fishbook.additional.entity.information.model.AdditionalService;
import com.fishbook.boat.dto.BoatDetailsDto;
import com.fishbook.boat.dto.BoatRegistrationDto;
import com.fishbook.boat.dto.BoatSpecificationsDto;
import com.fishbook.boat.dto.BoatUpdateDto;
import com.fishbook.boat.model.Boat;
import com.fishbook.boat.model.BoatType;
import com.fishbook.boat.service.BoatService;
import com.fishbook.entity.dto.EntityBasicInfoDto;
import com.fishbook.location.dto.LocationDto;
import com.fishbook.location.service.LocationService;
import com.fishbook.storage.service.StorageService;
import com.fishbook.user.model.User;
import com.fishbook.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@RequestMapping("/api/boats")
public class BoatController {
    private final BoatService boatService;
    private final LocationService locationService;
    private final UserService userService;
    private final StorageService storageService;

    @Autowired
    public BoatController(BoatService boatService, LocationService locationService, UserService userService, StorageService storageService) {
        this.boatService = boatService;
        this.locationService = locationService;
        this.userService = userService;
        this.storageService = storageService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_BOAT_OWNER')")
    public ResponseEntity<Long> registerNewBoat(@RequestBody BoatRegistrationDto boatRegistrationDto,  Principal principal){
        try {
            User user = userService.findByEmail(principal.getName());

            Boat boat = new Boat(boatRegistrationDto.getName(), boatRegistrationDto.getDescription(), boatRegistrationDto.getCancellationFee(), boatRegistrationDto.getPrice(),
                    false, boatRegistrationDto.getAddress(), boatRegistrationDto.getAppliedRules(), boatRegistrationDto.getAdditionalServices(),
                    boatRegistrationDto.getLength(), boatRegistrationDto.getMotors(), boatRegistrationDto.getPower(),
                    boatRegistrationDto.getMaxSpeed(), boatRegistrationDto.getMaxPeople(), boatRegistrationDto.getLoadCapacity(),
                    boatRegistrationDto.getFuelConsumption(), boatRegistrationDto.getMaxDistance(), boatRegistrationDto.getEnergyConsumption(),
                    BoatType.valueOf(boatRegistrationDto.getBoatType()), user, boatRegistrationDto.getEquipment());
            Long boatId = boatService.save(boat);
            return new ResponseEntity<>(boatId, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity getAllBoats(){
        List<EntityBasicInfoDto> boats = boatService.getAll().stream()
                .map(boat -> new EntityBasicInfoDto(boat.getId(), storageService.getPriorityImageUrl(boat.getImages()), boat.getName(), boat.getDescription(),
                        boat.getPricePerDay(), boat.getAddress().getCity().getName(), boat.getAddress().getCity().getCountry().getName(),
                        boat.getOwner().getFirstName() + " " + boat.getOwner().getLastName(), boat.getOwner().getEmail()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(boats, HttpStatus.OK);
    }

    @GetMapping(params = "ownerUsername")
    public ResponseEntity getBoatsForOwner(@RequestParam String ownerUsername){
        List<EntityBasicInfoDto> boats = boatService.getAllByOwnerUsername(ownerUsername).stream()
                .map(boat -> new EntityBasicInfoDto(boat.getId(), storageService.getPriorityImageUrl(boat.getImages()), boat.getName(), boat.getDescription(),
                        boat.getPricePerDay(), boat.getAddress().getCity().getName(), boat.getAddress().getCity().getCountry().getName(),
                        boat.getOwner().getFirstName() + " " + boat.getOwner().getLastName(), boat.getOwner().getEmail()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(boats, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getBoatDetails(@PathVariable Long id){
        Optional<Boat> boatOptional = boatService.findById(id);
        if(boatOptional.isEmpty()){
            return new ResponseEntity("Boat with that id doesn't exist", HttpStatus.NOT_FOUND);
        }
        Boat boat = boatOptional.get();
        return new ResponseEntity(new BoatDetailsDto(boat.getId(), storageService.getImageUrls(boat.getImages()), boat.getName(), boat.getOwner().getFullName(),
                boat.getDescription(), 0.0, boat.getPricePerDay(), boat.getCancellationFee(), new LocationDto(boat.getAddress().getAddress(), boat.getAddress().getCity().getName(),
                boat.getAddress().getCity().getCountry().getName(), boat.getAddress().getLongitude(), boat.getAddress().getLatitude()), new BoatSpecificationsDto(boat.getBoatType().toString(),
                boat.getMaxNumberOfPeople(), boat.getLength(), boat.getLoadCapacity(), boat.getMaxSpeed(), boat.getPower(), boat.getMotors(), boat.getFuelConsumption(), boat.getMaxDistance(),
                boat.getEnergyConsumption()), boat.getOwner().getEmail(), boat.getRules().stream().map(rule -> rule.getDescription()).collect(Collectors.toList()),
                boat.getNavigationEquipment().stream().map(equipment -> equipment.getName()).collect(Collectors.toList()),
                boat.getFishingEquipment().stream().map(equipment -> equipment.getName()).collect(Collectors.toList()), new ArrayList<>(boat.getAdditionalServices())), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BOAT_OWNER')")
    public ResponseEntity deleteBoat(@PathVariable Long id, Authentication authentication){
        Optional<Boat> boat = boatService.findById(id);
        if(boat.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User userDetails = (User) authentication.getPrincipal();
        if(Objects.equals(userDetails.getRole().getName(), "ROLE_BOAT_OWNER") && !Objects.equals(boat.get().getOwner().getId(), userDetails.getId())){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        boatService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('BOAT_OWNER')")
    public ResponseEntity updateBoat(@PathVariable Long id, @RequestBody BoatUpdateDto dto, Authentication authentication){
        Optional<Boat> boat = boatService.findById(id);
        if(boat.isEmpty() || !Objects.equals(boat.get().getId(), dto.getId())){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User userDetails = (User) authentication.getPrincipal();
        if(!Objects.equals(boat.get().getOwner().getId(), userDetails.getId())){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Boat updatedBoat = update(boat.get(), dto);

        boatService.save(updatedBoat);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Boat update(Boat boat, BoatUpdateDto dto){
        boat.setName(dto.getName());
        boat.setDescription(dto.getDescription());
        boat.setAddress(dto.getAddress());
        boat.setBoatType(BoatType.valueOf(dto.getBoatType()));
        boat.setMaxNumberOfPeople(dto.getMaxPeople());
        boat.setLength(dto.getLength());
        boat.setLoadCapacity(dto.getLoadCapacity());
        boat.setMaxSpeed(dto.getMaxSpeed());
        boat.setPower(dto.getPower());
        boat.setMotors(dto.getMotors());
        boat.setFuelConsumption(dto.getFuelConsumption());
        boat.setMaxDistance(dto.getMaxDistance());
        boat.setEnergyConsumption(dto.getEnergyConsumption());
        boat.setEquipment(dto.getEquipment());
        boat.setPricePerDay(dto.getPrice());
        boat.setCancellationFee(dto.getCancellationFee());
        boat.setAdditionalServices(dto.getAdditionalServices());
        boat.setImages(dto.getImages());

        return boat;
    }
}
