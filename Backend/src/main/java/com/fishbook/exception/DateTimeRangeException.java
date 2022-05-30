package com.fishbook.exception;

public class DateTimeRangeException extends RuntimeException {

    public DateTimeRangeException() {
        super("The inserted date range is invalid. Start date must be before end date.");
    }
}
