package com.fishbook.boat.model;

import com.fishbook.additional.entity.information.model.AdditionalService;
import com.fishbook.additional.entity.information.model.Rule;
import com.fishbook.additional.entity.information.model.Equipment;
import com.fishbook.location.model.Address;
import com.fishbook.user.model.User;
import com.fishbook.entity.model.Entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@javax.persistence.Entity
@Table(name = "Boats")
public class Boat extends Entity {
    @Column(nullable = false)
    private Double length;

    @Column(nullable = false)
    private Integer motors;

    @Column(nullable = false)
    private Integer power;

    @Column(nullable = false)
    private Integer maxSpeed;

    @Column(nullable = false)
    private Integer maxNumberOfPeople;

    @Column(nullable = false)
    private Integer loadCapacity;

    @Column(nullable = false)
    private Integer fuelConsumption;

    @Column(nullable = false)
    private Integer maxDistance;

    @Column(nullable = false)
    private Integer energyConsumption;

    @Enumerated(EnumType.STRING)
    private BoatType boatType;

    @ManyToMany
    @JoinTable(name = "boatEquipment", joinColumns = @JoinColumn(name = "boat_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "equipment_id", referencedColumnName = "id"))
    private Set<Equipment> equipment = new HashSet<>();

    public Boat() {}


    public Boat(String name, String description, Double cancellationFee, Double pricePerDay, Boolean isDeleted, Address address, Set<Rule> rules, Set<AdditionalService> additionalServices, Double length, Integer motors, Integer power, Integer maxSpeed, Integer maxNumberOfPeople, Integer loadCapacity, Integer fuelConsumption, Integer maxDistance, Integer energyConsumption, BoatType boatType, User owner, Set<Equipment> equipment) {
        super(name, description, cancellationFee, pricePerDay, isDeleted, owner, address, rules, additionalServices);
        this.length = length;
        this.motors = motors;
        this.power = power;
        this.maxSpeed = maxSpeed;
        this.maxNumberOfPeople = maxNumberOfPeople;
        this.loadCapacity = loadCapacity;
        this.fuelConsumption = fuelConsumption;
        this.maxDistance = maxDistance;
        this.energyConsumption = energyConsumption;
        this.boatType = boatType;
        this.equipment = equipment;
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

    public Integer getMaxNumberOfPeople() {
        return maxNumberOfPeople;
    }

    public void setMaxNumberOfPeople(Integer maxNumberOfPeople) {
        this.maxNumberOfPeople = maxNumberOfPeople;
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

    public BoatType getBoatType() {
        return boatType;
    }

    public void setBoatType(BoatType boatType) {
        this.boatType = boatType;
    }

    public Set<Equipment> getEquipment() {
        return equipment;
    }

    public void setEquipment(Set<Equipment> equipment) {
        this.equipment = equipment;
    }
}
