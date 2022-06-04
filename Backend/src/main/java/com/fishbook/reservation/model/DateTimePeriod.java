package com.fishbook.reservation.model;

import com.fishbook.exception.DateTimeRangeException;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private LocalDateTime fromDateTime;

    @Column(nullable = false)
    private LocalDateTime toDateTime;

    @Version
    private Long version;

    public DateTimePeriod(LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        if (fromDateTime.isAfter(toDateTime)) {
            throw new DateTimeRangeException();
        }
        this.fromDateTime = fromDateTime;
        this.toDateTime = toDateTime;
    }

    public Boolean isOverlapping(DateTimePeriod period) {
        return this.getFromDateTime().isBefore(period.getToDateTime()) && period.getFromDateTime().isBefore(this.getToDateTime());
    }

    public Boolean isContaining(LocalDateTime start, LocalDateTime end) {
        return this.getFromDateTime().isBefore(start) && this.getToDateTime().isAfter(end);
    }

    public Boolean isEqual(LocalDateTime start, LocalDateTime end) {
        return this.getFromDateTime().isEqual(start) && this.getToDateTime().isEqual(end);
    }

    public Boolean isOverlapping(LocalDateTime start, LocalDateTime end) {
        return this.getFromDateTime().isBefore(end) && start.isBefore(this.getToDateTime());
    }
}
