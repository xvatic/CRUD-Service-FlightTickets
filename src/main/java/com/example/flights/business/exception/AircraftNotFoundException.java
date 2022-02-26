package com.example.flights.business.exception;

public class AircraftNotFoundException extends Exception {
    public AircraftNotFoundException() {
        super("AIRCRAFT NOT FOUND");
    }
}
