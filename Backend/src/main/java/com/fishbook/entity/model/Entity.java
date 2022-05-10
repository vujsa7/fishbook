package com.fishbook.entity.model;

import com.fishbook.additional.entity.information.model.AdditionalService;
import com.fishbook.additional.entity.information.model.Rule;
import com.fishbook.location.model.Address;
import com.fishbook.storage.model.EntityImage;
import com.fishbook.user.model.User;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;

@javax.persistence.Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Where(clause = "is_deleted = false")
public abstract class Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double cancellationFee;

    @Column(nullable = false)
    private Double pricePerDay;

    @Column(nullable = false)
    private Boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToMany
    @JoinTable(name = "entity_rule", joinColumns = @JoinColumn(name = "entity_id"), inverseJoinColumns = @JoinColumn(name = "rule_id"))
    private Set<Rule> rules;

    @ManyToMany
    @JoinTable(name = "entity_additional_service", joinColumns = @JoinColumn(name = "entity_id"), inverseJoinColumns = @JoinColumn(name = "service_id"))
    private Set<AdditionalService> additionalServices;

    @OneToMany(mappedBy = "entity")
    private Set<EntityImage> images;

    public Entity() {}

    public Entity(String name, String description, Double cancellationFee, Double pricePerDay, Boolean isDeleted, User owner, Address address, Set<Rule> rules, Set<AdditionalService> additionalServices) {
        this.name = name;
        this.description = description;
        this.cancellationFee = cancellationFee;
        this.pricePerDay = pricePerDay;
        this.isDeleted = isDeleted;
        this.owner = owner;
        this.address = address;
        this.rules = rules;
        this.additionalServices = additionalServices;
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

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
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
}
