package com.fishbook.reservation.model;

import com.fishbook.additional.entity.information.model.AdditionalService;
import com.fishbook.entity.model.Entity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReservationOptions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime startDateTime;

    @Column(nullable = false)
    private LocalDateTime endDateTime;

    @Column
    private Integer maxNumberOfPeople;

    @ManyToMany
    private Set<AdditionalService> additionalServices;

    @ManyToOne
    private Entity entity;

    @Column(nullable = false)
    private Double price;

    public ReservationOptions(LocalDateTime startDateTime, LocalDateTime endDateTime, Integer maxNumberOfPeople,
                              Set<AdditionalService> additionalServices) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.maxNumberOfPeople = maxNumberOfPeople;
        this.additionalServices = additionalServices;
    }

    public ReservationOptions(LocalDateTime start, LocalDateTime end, Integer maxNumberOfPeople, HashSet<AdditionalService> additionalServices, Entity entity, Double price) {
        this.startDateTime = start;
        this.endDateTime = end;
        this.maxNumberOfPeople = maxNumberOfPeople;
        this.additionalServices = additionalServices;
        this.entity = entity;
        this.price = price;
    }
}
