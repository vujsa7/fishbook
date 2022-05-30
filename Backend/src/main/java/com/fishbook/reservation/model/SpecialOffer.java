package com.fishbook.reservation.model;

import com.fishbook.additional.entity.information.model.AdditionalService;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Version;
import java.time.LocalDateTime;
import java.util.Set;

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

    @Builder
    public SpecialOffer(LocalDateTime startDateTime, LocalDateTime endDateTime, Integer maxNumberOfPeople,
                        Set<AdditionalService> additionalServices, Double discount) {
        super(startDateTime, endDateTime, maxNumberOfPeople, additionalServices);
        this.discount = discount;
    }

    public Boolean isOverlapping(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return this.getStartDateTime().isAfter(startDateTime) && this.getStartDateTime().isBefore(endDateTime) ||
                this.getEndDateTime().isAfter(startDateTime) && this.getEndDateTime().isBefore(endDateTime) ||
                startDateTime.isAfter(this.getStartDateTime()) && startDateTime.isBefore(this.getEndDateTime()) ||
                endDateTime.isAfter(this.getStartDateTime()) && endDateTime.isBefore(this.getEndDateTime());
    }
}
