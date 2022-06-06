package com.fishbook.reservation.model;

import com.fishbook.additional.entity.information.model.AdditionalService;
import com.fishbook.reports.model.BuyerReport;
import com.fishbook.user.model.User;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.HashSet;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Reservation extends ReservationOptions {

    @ManyToOne
    private User client;

    private Boolean isCancelled;

    private Boolean loyaltyPointsAdded;

    @OneToOne(mappedBy = "reservation")
    private BuyerReport buyerReport;

    @OneToOne(mappedBy = "reservation")
    private ReservationReview reservationReview;

    public Reservation(LocalDateTime startDateTime, LocalDateTime endDateTime, Integer maxNumberOfPeople, Set<AdditionalService> additionalServices) {
        super(startDateTime, endDateTime, maxNumberOfPeople, additionalServices);
    }

    public Boolean isOverlapping(Reservation reservation) {
        return this.getStartDateTime().isBefore(reservation.getEndDateTime()) && reservation.getStartDateTime().isBefore(this.getEndDateTime());
    }

    public Boolean isOverlapping(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return this.getStartDateTime().isBefore(endDateTime) && startDateTime.isBefore(this.getEndDateTime());
    }

    public Boolean inRange(SellerAvailability sellerAvailability) {
        return sellerAvailability.getFromDateTime().isBefore(this.getStartDateTime()) && sellerAvailability.getToDateTime().isAfter(this.getEndDateTime());
    }

    public boolean inRange(EntityAvailability entityAvailability) {
        return entityAvailability.getFromDateTime().isBefore(this.getStartDateTime()) && entityAvailability.getToDateTime().isAfter(this.getEndDateTime());
    }

    public Reservation(LocalDateTime start, LocalDateTime end, Integer maxNumberOfPeople, Double price, Set<AdditionalService> additionalServices, com.fishbook.entity.model.Entity entity, User user) {
            super(start, end, maxNumberOfPeople, additionalServices, entity, price);
        this.client = user;
        this.isCancelled = false;
    }

    public Boolean isFinished() {
        return getEndDateTime().isBefore(LocalDateTime.now());
    }

}
