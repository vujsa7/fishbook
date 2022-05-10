package com.fishbook.fishing.lesson.dto;

import com.fishbook.additional.entity.information.model.AdditionalService;
import com.fishbook.additional.entity.information.model.Equipment;
import com.fishbook.additional.entity.information.model.Rule;
import com.fishbook.storage.model.EntityImage;

import java.util.Set;

public class FishingLessonDto {
    private Long id;
    private String name;
    private String description;
    private Double cancellationFee;
    private Double pricePerDay;
    private String country;
    private String city;
    private String address;
    private Set<Rule> rules;
    private Set<AdditionalService> additionalServices;
    private Set<EntityImage> images;
    private String instructorFirstName;
    private String instructorLastName;
    private String instructorBiography;
    private Integer maxNumberOfPeople;
    private Set<Equipment> fishingEquipment;

    public FishingLessonDto() {
    }

    public FishingLessonDto(Long id, String name, String description, Double cancellationFee, Double pricePerDay, String country, String city, String address, Set<Rule> rules, Set<AdditionalService> additionalServices, Set<EntityImage> images, String instructorFirstName, String instructorLastName, String instructorBiography, Integer maxNumberOfPeople, Set<Equipment> fishingEquipment) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cancellationFee = cancellationFee;
        this.pricePerDay = pricePerDay;
        this.country = country;
        this.city = city;
        this.address = address;
        this.rules = rules;
        this.additionalServices = additionalServices;
        this.images = images;
        this.instructorFirstName = instructorFirstName;
        this.instructorLastName = instructorLastName;
        this.instructorBiography = instructorBiography;
        this.maxNumberOfPeople = maxNumberOfPeople;
        this.fishingEquipment = fishingEquipment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getCancellationFee() {
        return cancellationFee;
    }

    public void setCancellationFee(Double cancellationFee) {
        this.cancellationFee = cancellationFee;
    }

    public Double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(Double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Rule> getRules() {
        return rules;
    }

    public void setRules(Set<Rule> rules) {
        this.rules = rules;
    }

    public Set<AdditionalService> getAdditionalServices() {
        return additionalServices;
    }

    public void setAdditionalServices(Set<AdditionalService> additionalServices) {
        this.additionalServices = additionalServices;
    }

    public Set<EntityImage> getImages() {
        return images;
    }

    public void setImages(Set<EntityImage> images) {
        this.images = images;
    }

    public String getInstructorFirstName() {
        return instructorFirstName;
    }

    public void setInstructorFirstName(String instructorFirstName) {
        this.instructorFirstName = instructorFirstName;
    }

    public String getInstructorLastName() {
        return instructorLastName;
    }

    public void setInstructorLastName(String instructorLastName) {
        this.instructorLastName = instructorLastName;
    }

    public String getInstructorBiography() {
        return instructorBiography;
    }

    public void setInstructorBiography(String instructorBiography) {
        this.instructorBiography = instructorBiography;
    }

    public Integer getMaxNumberOfPeople() {
        return maxNumberOfPeople;
    }

    public void setMaxNumberOfPeople(Integer maxNumberOfPeople) {
        this.maxNumberOfPeople = maxNumberOfPeople;
    }

    public Set<Equipment> getFishingEquipment() {
        return fishingEquipment;
    }

    public void setFishingEquipment(Set<Equipment> fishingEquipment) {
        this.fishingEquipment = fishingEquipment;
    }
}
