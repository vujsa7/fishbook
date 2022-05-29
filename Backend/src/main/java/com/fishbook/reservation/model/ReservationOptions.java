package com.fishbook.reservation.model;

import com.fishbook.additional.entity.information.model.AdditionalService;
import com.fishbook.entity.model.Entity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
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
    private Date startDateTime;

    @Column(nullable = false)
    private Date endDateTime;

    @Column(nullable = false)
    private Integer maxNumberOfPeople;

    @ManyToMany
    private Set<AdditionalService> additionalServices;

    @ManyToOne
    private Entity entity;

    @Column(nullable = false)
    private Double price;
}
