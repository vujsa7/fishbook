package com.fishbook.exception;

public class ReservationNotFinishedException extends RuntimeException {

    public ReservationNotFinishedException() {
        super("Reservation is not finished");
    }
}
