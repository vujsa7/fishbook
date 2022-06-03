package com.fishbook.reservation.model;

import com.fishbook.additional.entity.information.model.AdditionalService;
import com.fishbook.user.model.User;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Reservation extends ReservationOptions {

    @ManyToOne
    private User client;

    public Reservation(LocalDateTime startDateTime, LocalDateTime endDateTime, Integer maxNumberOfPeople, Set<AdditionalService> additionalServices) {
        super(startDateTime, endDateTime, maxNumberOfPeople, additionalServices);
    }

    public Boolean isOverlapping(Reservation reservation) {
        return this.getStartDateTime().isBefore(reservation.getEndDateTime()) && reservation.getStartDateTime().isBefore(this.getEndDateTime());
    }

    public boolean inRange(SellerAvailability sellerAvailability) {
        return sellerAvailability.getFromDateTime().isBefore(this.getStartDateTime()) && sellerAvailability.getToDateTime().isAfter(this.getEndDateTime());
    }

    public boolean inRange(EntityAvailability entityAvailability) {
        return entityAvailability.getFromDateTime().isBefore(this.getStartDateTime()) && entityAvailability.getToDateTime().isAfter(this.getEndDateTime());
    }
}
