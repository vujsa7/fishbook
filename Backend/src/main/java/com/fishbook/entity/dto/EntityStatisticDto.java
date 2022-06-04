package com.fishbook.entity.dto;

public class EntityStatisticDto {
    private Long id;
    private String name;
    private Double rating;
    private Integer reservations;

    public EntityStatisticDto(Long id, String name, Double rating, Integer reservations) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.reservations = reservations;
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

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getReservations() {
        return reservations;
    }

    public void setReservations(Integer reservations) {
        this.reservations = reservations;
    }
}
