package com.fishbook.fishing.lesson.model;

import com.fishbook.additional.entity.information.model.AdditionalService;
import com.fishbook.additional.entity.information.model.Equipment;
import com.fishbook.additional.entity.information.model.Rule;
import com.fishbook.entity.model.Entity;
import com.fishbook.location.model.Address;
import com.fishbook.user.model.User;

import javax.persistence.*;
import java.util.Set;

@javax.persistence.Entity
public class FishingLesson extends Entity {

    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private User instructor;

    @Column(nullable = false)
    private String instructorBiography;

    @Column(nullable = false)
    private Integer maxNumberOfPeople;

    @ManyToMany
    @JoinTable(name = "fishing_equipment", joinColumns = @JoinColumn(name = "lesson_id"), inverseJoinColumns = @JoinColumn(name = "equipment_id"))
    private Set<Equipment> fishingEquipment;

    public FishingLesson() {
    }

    public FishingLesson(String name, String description, Double cancellationFee, Boolean isDeleted, Address address, Set<Rule> rules, Set<AdditionalService> additionalServices, User instructor, String instructorBiography, Integer maxNumberOfPeople, Set<Equipment> fishingEquipment) {
        super(name, description, cancellationFee, isDeleted, address, rules, additionalServices);
        this.instructor = instructor;
        this.instructorBiography = instructorBiography;
        this.maxNumberOfPeople = maxNumberOfPeople;
        this.fishingEquipment = fishingEquipment;
    }

    public User getInstructor() {
        return instructor;
    }

    public void setInstructor(User instructor) {
        this.instructor = instructor;
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
