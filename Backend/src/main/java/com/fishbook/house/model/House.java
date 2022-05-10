package com.fishbook.house.model;

import com.fishbook.additional.entity.information.model.AdditionalService;
import com.fishbook.additional.entity.information.model.Rule;
import com.fishbook.entity.model.Entity;
import com.fishbook.location.model.Address;
import com.fishbook.user.model.User;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@javax.persistence.Entity
public class House extends Entity {
    @Column(nullable = false)
    private Integer maxNumberOfPeople;

    @OneToMany(mappedBy = "house")
    private List<Room> rooms;

    public House() {
    }

    public House(String name, String description, Double cancellationFee, Double pricePerDay, Boolean isDeleted, Address address, Set<Rule> rules, Set<AdditionalService> additionalServices, Integer maxNumberOfPeople, User owner, List<Room> rooms) {
        super(name, description, cancellationFee, pricePerDay, isDeleted, owner, address, rules, additionalServices);
        this.maxNumberOfPeople = maxNumberOfPeople;
        this.rooms = rooms;
    }

    public Integer getMaxNumberOfPeople() {
        return maxNumberOfPeople;
    }

    public void setMaxNumberOfPeople(Integer maxNumberOfPeople) {
        this.maxNumberOfPeople = maxNumberOfPeople;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}
