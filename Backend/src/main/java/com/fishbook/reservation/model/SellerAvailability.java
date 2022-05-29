package com.fishbook.reservation.model;

import com.fishbook.user.model.User;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SellerAvailability extends DateTimePeriod {

    @ManyToOne
    private User seller;

    public SellerAvailability(LocalDateTime fromDateTime, LocalDateTime toDateTime, User seller) {
        super(fromDateTime, toDateTime);
        this.seller = seller;
    }
}
