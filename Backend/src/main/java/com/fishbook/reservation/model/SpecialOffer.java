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
        return this.getStartDateTime().isBefore(endDateTime) && startDateTime.isBefore(this.getEndDateTime());
    }

    public Boolean inRange(SellerAvailability sellerAvailability) {
        return sellerAvailability.getFromDateTime().isBefore(this.getStartDateTime()) && sellerAvailability.getToDateTime().isAfter(this.getEndDateTime());
    }

    public Boolean inRange(EntityAvailability entityAvailability) {
        return entityAvailability.getFromDateTime().isBefore(this.getStartDateTime()) && entityAvailability.getToDateTime().isAfter(this.getEndDateTime());
    }
}
