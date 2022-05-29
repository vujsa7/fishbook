package com.fishbook.house.dto;

import com.fishbook.additional.entity.information.model.AdditionalService;
import com.fishbook.additional.entity.information.model.Rule;
import com.fishbook.house.model.Room;
import com.fishbook.location.model.Address;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HouseRegistrationDto {
    private String name;
    private String description;
    private Address address;
    private String city;
    private Integer maxPeople;
    private Double cancellationFee;
    private Double price;
    private Set<Rule> appliedRules = new HashSet<>();
    private List<Room> rooms = new ArrayList<>();
    private Set<AdditionalService> additionalServices = new HashSet<>();

    public HouseRegistrationDto() {
    }

    public HouseRegistrationDto(String name, String description, Address address, String city, Integer maxPeople, Double cancellationFee, Double price, Set<Rule> appliedRules, List<Room> rooms, Set<AdditionalService> additionalServices) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.city = city;
        this.maxPeople = maxPeople;
        this.cancellationFee = cancellationFee;
        this.price = price;
        this.appliedRules = appliedRules;
        this.rooms = rooms;
        this.additionalServices = additionalServices;
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

    public Integer getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(Integer maxPeople) {
        this.maxPeople = maxPeople;
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

    public Set<Rule> getAppliedRules() {
        return appliedRules;
    }

    public void setAppliedRules(Set<Rule> appliedRules) {
        this.appliedRules = appliedRules;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public Set<AdditionalService> getAdditionalServices() {
        return additionalServices;
    }

    public void setAdditionalServices(Set<AdditionalService> additionalServices) {
        this.additionalServices = additionalServices;
    }
}
