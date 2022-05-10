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

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name="userId", nullable = false)
    private User owner;

    @OneToMany(mappedBy = "house")
    private List<Room> rooms;

    public House() {
    }

    public House(String name, String description, Double cancellationFee, Double pricePerDay, Boolean isDeleted, Address address, Set<Rule> rules, Set<AdditionalService> additionalServices, Integer maxNumberOfPeople, User owner, List<Room> rooms) {
        super(name, description, cancellationFee, pricePerDay, isDeleted, address, rules, additionalServices);
        this.maxNumberOfPeople = maxNumberOfPeople;
        this.owner = owner;
        this.rooms = rooms;
    }

    public Integer getMaxNumberOfPeople() {
        return maxNumberOfPeople;
    }

    public void setMaxNumberOfPeople(Integer maxNumberOfPeople) {
        this.maxNumberOfPeople = maxNumberOfPeople;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}
