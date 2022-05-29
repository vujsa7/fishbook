package com.fishbook.reservation.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Version;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SpecialOffer extends ReservationOptions {

    @Column(nullable = false)
    private Double discount;

    @Version
    private Long version;
}
