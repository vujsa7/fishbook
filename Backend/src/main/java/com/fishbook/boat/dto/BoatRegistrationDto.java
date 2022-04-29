package com.fishbook.boat.dto;

import com.fishbook.additional.entity.information.model.AdditionalService;
import com.fishbook.additional.entity.information.model.AppliedRule;
import com.fishbook.storage.model.EntityImage;
import com.fishbook.additional.entity.information.model.Equipment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BoatRegistrationDto {
    private String name;
    private String description;
    private String address;
    private String city;
    private Double length;
    private Integer motors;
    private Integer power;
    private Integer maxSpeed;
    private Integer maxPeople;
    private Integer loadCapacity;
    private Integer fuelConsumption;
    private Integer maxDistance;
    private Integer energyConsumption;
    private Integer advancePayment;
    private Integer price;
    private String boatType;
    private Set<AppliedRule> appliedRules = new HashSet<>();
    private Set<Equipment> equipment = new HashSet<>();
    private Set<AdditionalService> additionalServices = new HashSet<>();
    private List<EntityImage> images = new ArrayList<>();

    public BoatRegistrationDto() {}

    public BoatRegistrationDto(String name, String description, String address, String city, Double length, Integer motors, Integer power, Integer maxSpeed, Integer maxPeople, Integer loadCapacity, Integer fuelConsumption, Integer maxDistance, Integer energyConsumption, Integer advancePayment, Integer price, String boatType) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.city = city;
        this.length = length;
        this.motors = motors;
        this.power = power;
        this.maxSpeed = maxSpeed;
        this.maxPeople = maxPeople;
        this.loadCapacity = loadCapacity;
        this.fuelConsumption = fuelConsumption;
        this.maxDistance = maxDistance;
        this.energyConsumption = energyConsumption;
        this.advancePayment = advancePayment;
        this.price = price;
        this.boatType = boatType;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Integer getMotors() {
        return motors;
    }

    public void setMotors(Integer motors) {
        this.motors = motors;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public Integer getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Integer maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public Integer getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(Integer maxPeople) {
        this.maxPeople = maxPeople;
    }

    public Integer getLoadCapacity() {
        return loadCapacity;
    }

    public void setLoadCapacity(Integer loadCapacity) {
        this.loadCapacity = loadCapacity;
    }

    public Integer getFuelConsumption() {
        return fuelConsumption;
    }

    public void setFuelConsumption(Integer fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    public Integer getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(Integer maxDistance) {
        this.maxDistance = maxDistance;
    }

    public Integer getEnergyConsumption() {
        return energyConsumption;
    }

    public void setEnergyConsumption(Integer energyConsumption) {
        this.energyConsumption = energyConsumption;
    }

    public Integer getAdvancePayment() {
        return advancePayment;
    }

    public void setAdvancePayment(Integer advancePayment) {
        this.advancePayment = advancePayment;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getBoatType() {
        return boatType;
    }

    public void setBoatType(String boatType) {
        this.boatType = boatType;
    }

    public Set<AppliedRule> getAppliedRules() {
        return appliedRules;
    }

    public void setAppliedRules(Set<AppliedRule> appliedRules) {
        this.appliedRules = appliedRules;
    }

    public Set<Equipment> getEquipment() {
        return equipment;
    }

    public void setEquipment(Set<Equipment> equipment) {
        this.equipment = equipment;
    }

    public Set<AdditionalService> getAdditionalServices() {
        return additionalServices;
    }

    public void setAdditionalServices(Set<AdditionalService> additionalServices) {
        this.additionalServices = additionalServices;
    }

    public List<EntityImage> getImages() {
        return images;
    }

    public void setImages(List<EntityImage> images) {
        this.images = images;
    }
}
