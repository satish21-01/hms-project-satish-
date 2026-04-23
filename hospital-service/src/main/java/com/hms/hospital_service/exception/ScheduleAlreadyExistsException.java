package com.hms.hospital_service.exception;


public class ScheduleAlreadyExistsException extends RuntimeException {
    public ScheduleAlreadyExistsException(String message) {
        super(message);
    }
}