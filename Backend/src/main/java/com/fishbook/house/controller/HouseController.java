package com.fishbook.house.controller;

import com.fishbook.additional.entity.information.service.AdditionalServiceService;
import com.fishbook.entity.dto.EntityBasicInfoDto;
import com.fishbook.entity.dto.EntityStatisticDto;
import com.fishbook.house.dto.HouseDetailsDto;
import com.fishbook.house.dto.HouseRegistrationDto;
import com.fishbook.house.dto.HouseSpecificationsDto;
import com.fishbook.house.dto.HouseUpdateDto;
import com.fishbook.house.model.House;
import com.fishbook.house.service.HouseService;
import com.fishbook.location.dto.LocationDto;
import com.fishbook.location.service.LocationService;
import com.fishbook.reservation.dto.SpecialOfferPreviewDto;
import com.fishbook.reservation.model.SpecialOffer;
import com.fishbook.reservation.service.ReservationService;
import com.fishbook.reservation.service.SpecialOfferService;
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
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/houses")
public class HouseController {
    private final HouseService houseService;
    private final UserService userService;
    private final StorageService storageService;
    private final SpecialOfferService specialOfferService;
    private final ReservationService reservationService;

    @Autowired
    public HouseController(HouseService houseService, UserService userService, StorageService storageService, SpecialOfferService specialOfferService, ReservationService reservationService) {
        this.houseService = houseService;
        this.reservationService = reservationService;
        this.userService = userService;
        this.storageService = storageService;
        this.specialOfferService = specialOfferService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_HOUSE_OWNER')")
    public ResponseEntity registerNewHouse(@RequestBody HouseRegistrationDto houseRegistrationDto, Principal principal){
        try {
            User user = userService.findByEmail(principal.getName());

            House house = new House(houseRegistrationDto.getName(), houseRegistrationDto.getDescription(), houseRegistrationDto.getCancellationFee(),
                    houseRegistrationDto.getPrice(), false, houseRegistrationDto.getAddress(), houseRegistrationDto.getAppliedRules(), houseRegistrationDto.getAdditionalServices(),
                    houseRegistrationDto.getMaxPeople(), user, houseRegistrationDto.getRooms());
            Long houseId = houseService.save(house);
            return new ResponseEntity<>(houseId, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity getAllHouses(){
        List<EntityBasicInfoDto> houses = houseService.getAll().stream()
                .map(house -> new EntityBasicInfoDto(house.getId(), storageService.getPriorityImageUrl(house.getImages()), house.getName(), house.getDescription(),
                        house.getPricePerDay(), house.getAddress().getCity().getName(), house.getAddress().getCity().getCountry().getName(),
                        house.getOwner().getFirstName() + " " + house.getOwner().getLastName(), house.getOwner().getEmail()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(houses, HttpStatus.OK);
    }

    @GetMapping(value = "/statistics", params = "ownerUsername")
    public ResponseEntity getHouseStatistics(@RequestParam String ownerUsername){
        List<EntityStatisticDto> houses = houseService.getAllByOwnerUsername(ownerUsername).stream()
                .map(house -> new EntityStatisticDto(house.getId(), house.getName(), house.getRating(), reservationService.getNumberOfReservations(house)))
                .collect(Collectors.toList());

        return new ResponseEntity<>(houses, HttpStatus.OK);
    }

    @GetMapping(params = "ownerUsername")
    public ResponseEntity getHousesForOwner(@RequestParam String ownerUsername){
        List<EntityBasicInfoDto> houses = houseService.getAllByOwnerUsername(ownerUsername).stream()
                .map(house -> new EntityBasicInfoDto(house.getId(), storageService.getPriorityImageUrl(house.getImages()), house.getName(), house.getDescription(),
                        house.getPricePerDay(), house.getAddress().getCity().getName(), house.getAddress().getCity().getCountry().getName(),
                        house.getOwner().getFirstName() + " " + house.getOwner().getLastName(), house.getOwner().getEmail()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(houses, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getHouseDetails(@PathVariable Long id){
        Optional<House> houseOptional = houseService.findById(id);
        if(houseOptional.isEmpty()){
            return new ResponseEntity("House with that id doesn't exist", HttpStatus.NOT_FOUND);
        }
        House house = houseOptional.get();
        List<SpecialOffer> specialOffers = new ArrayList(specialOfferService.getSpecialOffersByEntityId(house.getId()));
        List<SpecialOfferPreviewDto> specialOfferPreviews = specialOffers.stream().map(s -> new SpecialOfferPreviewDto(s.getId(), s.getStartDateTime(), s.getEndDateTime(), s.getPrice()*(100 + s.getDiscount())/100, s.getPrice())).collect(Collectors.toList());
        return new ResponseEntity(new HouseDetailsDto(house.getId(), storageService.getImageUrls(house.getImages()), house.getName(), house.getOwner().getFullName(),
                house.getDescription(), 0.0, house.getPricePerDay(), house.getCancellationFee(), new LocationDto(house.getAddress().getAddress(), house.getAddress().getCity().getName(),
                house.getAddress().getCity().getCountry().getName(), house.getAddress().getLongitude(), house.getAddress().getLatitude()), new HouseSpecificationsDto(house.getRooms().size(), house.getBedsByRooms(), house.getBedCount()),
                house.getOwner().getEmail(), house.getOwner().getId(), house.getRules().stream().map(rule -> rule.getDescription()).collect(Collectors.toList()), new ArrayList<>(house.getAdditionalServices()), specialOfferPreviews), HttpStatus.OK);

    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_HOUSE_OWNER')")
    public ResponseEntity deleteHouse(@PathVariable Long id, Authentication authentication){
        Optional<House> house = houseService.findById(id);
        if(house.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User userDetails = (User) authentication.getPrincipal();
        if(Objects.equals(userDetails.getRole().getName(), "ROLE_HOUSE_OWNER") && !Objects.equals(house.get().getOwner().getId(), userDetails.getId())){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        houseService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('HOUSE_OWNER')")
    public ResponseEntity updateHouse(@PathVariable Long id, @RequestBody HouseUpdateDto dto, Authentication authentication){
        Optional<House> house = houseService.findById(id);
        if(house.isEmpty() || !Objects.equals(house.get().getId(), dto.getId())){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User userDetails = (User) authentication.getPrincipal();
        if(!Objects.equals(house.get().getOwner().getId(), userDetails.getId())){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        House updatedHouse = update(house.get(), dto);

        houseService.save(updatedHouse);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private House update(House house, HouseUpdateDto dto) {
        house.setName(dto.getName());
        house.setDescription(dto.getDescription());
        house.setAddress(dto.getAddress());
        house.setMaxNumberOfPeople(dto.getMaxPeople());
        house.setRooms(dto.getRooms());
        house.setPricePerDay(dto.getPrice());
        house.setCancellationFee(dto.getCancellationFee());
        house.setAdditionalServices(dto.getAdditionalServices());
        house.setImages(dto.getImages());

        return house;
    }
}
