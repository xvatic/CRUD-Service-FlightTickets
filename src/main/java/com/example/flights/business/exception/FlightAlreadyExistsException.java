package com.example.flights.business.exception;

public class FlightAlreadyExistsException extends Exception{
    public FlightAlreadyExistsException(String message) {
        super(message);
    }
}
