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
@AllArgsConstructor
@ToString
public class SellerUnavailability extends DateTimePeriod {

    @ManyToOne
    private User seller;

    public SellerUnavailability(LocalDateTime start, LocalDateTime end, User seller) {
        super(start, end);
        this.seller = seller;
    }
}
