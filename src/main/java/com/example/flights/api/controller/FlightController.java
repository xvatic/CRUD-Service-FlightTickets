package com.example.flights.api.controller;

import com.example.flights.domain.entity.FlightEntity;
import com.example.flights.domain.entity.UserEntity;
import com.example.flights.business.exception.FlightAlreadyExistsException;
import com.example.flights.business.exception.FlightNotFoundException;
import com.example.flights.business.exception.UserNotFoundException;
import com.example.flights.business.service.FlightService;
import com.example.flights.api.dto.FlightDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;


@EnableWebMvc
@RestController
@RequestMapping("/flights")
public class FlightController {
    @Autowired
    private FlightService flightService;

    @PostMapping
    public ResponseEntity addFlight(@RequestBody FlightEntity flight) {
        try {
            flightService.addFlight(flight);
            return ResponseEntity.ok("SAVED");
        } catch (FlightAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("UNKNOWN EXCEPTION");
        }
    }

    @GetMapping
    public FlightDTO getFlight(@RequestParam Long id) throws Exception {
        try {
            return flightService.getFlight(id);
        } catch (FlightNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<FlightDTO>> getAllFlights() {
        return ResponseEntity.ok(flightService.getAllFlights());
    }

    @PutMapping
    public ResponseEntity updateFlightInformation(@RequestBody FlightEntity flight) {
        try {
            return ResponseEntity.ok(flightService.updateFlight(flight));
        } catch (FlightNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{flightId}")
    public ResponseEntity registerUserToFlight(@RequestBody UserEntity user, @PathVariable Long flightId) {
        try {
            return ResponseEntity.ok(flightService.registerUserToFlight(user, flightId));
        } catch (FlightNotFoundException | UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteFlight(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(flightService.deleteFlight(id));
        } catch (FlightNotFoundException e) {
            return ResponseEntity.badRequest().body("USER NOT FOUND");
        }
    }
}
