package com.fishbook.exception;

public class EntityNotAvailableException extends RuntimeException {

    public EntityNotAvailableException() {
        super("Entity is not available at the requested dates");
    }
}
