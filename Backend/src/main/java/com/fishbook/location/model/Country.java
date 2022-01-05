package com.fishbook.location.model;

import javax.persistence.*;
import java.util.Set;


@Entity
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "country")
//    private Set<City> cities;

    public Country() {}

    public Country(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public void setCities(Set<City> cities) {
//        this.cities = cities;
//    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

//    public Set<City> getCities() {
//        return cities;
//    }
}
