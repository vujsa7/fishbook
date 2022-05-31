package com.fishbook.reservation.model;

import lombok.*;
import com.fishbook.entity.model.Entity;

import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@javax.persistence.Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EntityAvailability extends DateTimePeriod {

    @ManyToOne
    private Entity entity;

    public EntityAvailability(LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        super(fromDateTime, toDateTime);
    }
}
