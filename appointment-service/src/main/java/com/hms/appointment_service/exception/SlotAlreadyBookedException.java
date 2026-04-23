package com.hms.appointment_service.exception;


public class SlotAlreadyBookedException extends RuntimeException {

    public SlotAlreadyBookedException(String message) {
        super(message);
    }
}
