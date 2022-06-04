package com.fishbook.reports.model;

import com.fishbook.reservation.model.Reservation;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Reservation reservation;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private Boolean clientArrived;

    @Column(nullable = false)
    private Boolean shouldGetPenalty;
}
