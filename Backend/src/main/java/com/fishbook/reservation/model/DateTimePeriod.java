package com.fishbook.reservation.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DateTimePeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date fromDateTime;

    @Column(nullable = false)
    private Date toDateTime;

    @Version
    private Long version;
}
