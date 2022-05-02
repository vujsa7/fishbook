package com.fishbook.boat.model;

import com.fishbook.additional.entity.information.model.AdditionalService;
import com.fishbook.additional.entity.information.model.Rule;
import com.fishbook.additional.entity.information.model.Equipment;
import com.fishbook.location.model.Address;
import com.fishbook.storage.model.EntityImage;
import com.fishbook.user.model.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Boats")
public class Boat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
    @JoinColumn(name = "addressId")
    private Address address;

    @Column(nullable = false)
    private Double length;

    @Column(nullable = false)
    private Integer motors;

    @Column(nullable = false)
    private Integer power;

    @Column(nullable = false)
    private Integer maxSpeed;

    @Column(nullable = false)
    private Integer maxPeople;

    @Column(nullable = false)
    private Integer loadCapacity;

    @Column(nullable = false)
    private Integer fuelConsumption;

    @Column(nullable = false)
    private Integer maxDistance;

    @Column(nullable = false)
    private Integer energyConsumption;

    @Column(nullable = false)
    private Integer advancePayment;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Boolean isDeleted;

    @Column(nullable = false)
    private Integer stars;

    @Enumerated(EnumType.STRING)
    private BoatType boatType;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name="owner_id", nullable = false)
    private User user;

    @ManyToMany
    @JoinTable(name = "boatRules", joinColumns = @JoinColumn(name = "boat_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "rule_id", referencedColumnName = "id"))
    private Set<Rule> appliedRules = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "boatEquipment", joinColumns = @JoinColumn(name = "boat_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "equipment_id", referencedColumnName = "id"))
    private Set<Equipment> equipment = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "boatServices", joinColumns = @JoinColumn(name = "boat_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "service_id", referencedColumnName = "id"))
    private Set<AdditionalService> additionalServices = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "boatImages", joinColumns = @JoinColumn(name = "boat_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "image_id", referencedColumnName = "id"))
    private Set<EntityImage> images = new HashSet<>();

    public Boat() {}

    public Boat(String name, String description, Address address, Double length, Integer motors, Integer power, Integer maxSpeed, Integer maxPeople, Integer loadCapacity, Integer fuelConsumption, Integer maxDistance, Integer energyConsumption, Integer advancePayment, Integer price, BoatType boatType, User user) {
        this.name = name;
        this.description = description;
        this.address = address;
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
        this.isDeleted = false;
        this.stars = 0;
        this.boatType = boatType;
        this.user = user;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public BoatType getBoatType() {
        return boatType;
    }

    public void setBoatType(BoatType boatType) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<EntityImage> getImages() {
        return images;
    }

    public void setImages(Set<EntityImage> images) {
        this.images = images;
    }

    public void addImage(EntityImage image) {
        images.add(image);
    }

    public void removeImage(EntityImage image) {
        images.remove(image);
    }
}
