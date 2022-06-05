package com.fishbook.exception;

public class EntityReservedException extends RuntimeException {

    public EntityReservedException() {
        super("Entity has active or future reservations");
    }
}
