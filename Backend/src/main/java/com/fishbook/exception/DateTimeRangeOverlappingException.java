package com.fishbook.exception;

public class DateTimeRangeOverlappingException extends RuntimeException {

    public DateTimeRangeOverlappingException() {
        super("The inserted date time range overlaps existing date time ranges.");
    }
}
