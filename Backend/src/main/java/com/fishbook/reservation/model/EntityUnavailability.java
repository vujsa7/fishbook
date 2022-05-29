package com.fishbook.reservation.model;

import com.fishbook.entity.model.Entity;
import lombok.*;

import javax.persistence.ManyToOne;

@javax.persistence.Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EntityUnavailability extends DateTimePeriod {

    @ManyToOne
    private com.fishbook.entity.model.Entity entity;
}
