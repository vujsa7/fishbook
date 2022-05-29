package com.fishbook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        // Create payload containing exception detail
        ApiException apiException = new ApiException(e.getMessage(), e, badRequest, ZonedDateTime.now(ZoneId.of("Z")));
        // Return response entity
        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(DateTimeRangeException.class)
    public ResponseEntity<Object> handleBadRequest(DateTimeRangeException ex) {
        ApiException apiException = new ApiException(ex.getMessage(), ex, HttpStatus.BAD_REQUEST, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DateTimeRangeOverlappingException.class)
    public ResponseEntity<Object> handleConflict(DateTimeRangeOverlappingException ex) {
        ApiException apiException = new ApiException(ex.getMessage(), ex, HttpStatus.CONFLICT, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(apiException, HttpStatus.CONFLICT);
    }
}
