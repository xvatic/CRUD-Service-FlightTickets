package com.example.flights.business.exception;

public class AircraftAlreadyExistsException extends Exception{
    public AircraftAlreadyExistsException() {
        super("AIRCRAFT ALREADY EXISTS");
    }
}
