package com.fishbook.boat.dto;

import com.fishbook.additional.entity.information.model.AdditionalService;
import com.fishbook.additional.entity.information.model.Rule;
import com.fishbook.additional.entity.information.model.Equipment;
import com.fishbook.location.model.Address;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

public class BoatRegistrationDto {
    @NotBlank
    private String name;

    @NotBlank
    private String description;

    private Address address;
    private String city;

    @NotNull
    private Double length;

    @NotNull
    private Integer motors;

    @NotNull
    private Integer power;

    @NotNull
    private Integer maxSpeed;

    @NotNull
    private Integer maxPeople;

    @NotNull
    private Integer loadCapacity;

    @NotNull
    private Integer fuelConsumption;

    @NotNull
    private Integer maxDistance;

    @NotNull
    private Integer energyConsumption;

    @NotNull
    private Double cancellationFee;

    @NotNull
    private Double price;
    private String boatType;
    private Set<Rule> appliedRules = new HashSet<>();
    private Set<Equipment> equipment = new HashSet<>();
    private Set<AdditionalService> additionalServices = new HashSet<>();

    public BoatRegistrationDto() {}

    public BoatRegistrationDto(String name, String description, Address address, String city, Double length, Integer motors, Integer power, Integer maxSpeed, Integer maxPeople, Integer loadCapacity, Integer fuelConsumption, Integer maxDistance, Integer energyConsumption, Double cancellationFee, Double price, String boatType) {
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
        this.cancellationFee = cancellationFee;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
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

    public Double getCancellationFee() {
        return cancellationFee;
    }

    public void setCancellationFee(Double cancellationFee) {
        this.cancellationFee = cancellationFee;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getBoatType() {
        return boatType;
    }

    public void setBoatType(String boatType) {
        this.boatType = boatType;
    }

    public Set<Rule> getAppliedRules() {
        return appliedRules;
    }

    public void setAppliedRules(Set<Rule> appliedRules) {
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
}
