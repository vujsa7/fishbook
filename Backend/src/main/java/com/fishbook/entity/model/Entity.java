package com.fishbook.entity.model;

import com.fishbook.additional.entity.information.model.AdditionalService;
import com.fishbook.additional.entity.information.model.Rule;
import com.fishbook.location.model.Address;
import com.fishbook.storage.model.EntityImage;

import javax.persistence.*;
import java.util.Set;

@javax.persistence.Entity
@Inheritance(strategy = InheritanceType.JOINED)
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
    private Boolean isDeleted;

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

    public Entity(String name, String description, Double cancellationFee, Boolean isDeleted, Address address, Set<Rule> rules, Set<AdditionalService> additionalServices) {
        this.name = name;
        this.description = description;
        this.cancellationFee = cancellationFee;
        this.isDeleted = isDeleted;
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

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
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
