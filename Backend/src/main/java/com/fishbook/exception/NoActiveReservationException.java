package com.fishbook.exception;

public class NoActiveReservationException extends RuntimeException {

    public NoActiveReservationException() {
        super("Client must have an active reservation for this action to be completed");
    }
}
