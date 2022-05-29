package com.fishbook.reservation.model;

import com.fishbook.user.model.User;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Reservation extends ReservationOptions {

    @ManyToOne
    private User client;
}
