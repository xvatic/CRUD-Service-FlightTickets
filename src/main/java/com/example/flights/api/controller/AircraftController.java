package com.example.flights.api.controller;

import com.example.flights.domain.entity.AircraftEntity;
import com.example.flights.business.exception.AircraftAlreadyExistsException;
import com.example.flights.business.exception.AircraftNotFoundException;
import com.example.flights.business.exception.FlightNotFoundException;
import com.example.flights.business.service.AircraftService;
import com.example.flights.api.dto.AircraftDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@RestController
@RequestMapping("/aircraft")
public class AircraftController {
    @Autowired
    private AircraftService aircraftService;

    @PostMapping
    public ResponseEntity addAircraft(@RequestBody AircraftEntity aircraft) {
        try {
            aircraftService.addAircraft(aircraft);
            return ResponseEntity.ok("SAVED");
        } catch (AircraftAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("UNKNOWN EXCEPTION");
        }
    }

    @GetMapping
    public AircraftDTO getFlight(@RequestParam Long id) throws Exception {
        try {
            return aircraftService.getAircraft(id);
        } catch (AircraftNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity updateAircraftInformation(@RequestBody AircraftEntity aircraft) {
        try {
            return ResponseEntity.ok(aircraftService.updateAircraft(aircraft));
        } catch (AircraftNotFoundException | AircraftAlreadyExistsException  e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{flightId}")
    public ResponseEntity chooseAircraftToFligt(@RequestBody AircraftEntity aircraft, @PathVariable Long flightId) {
        try {
            return ResponseEntity.ok(aircraftService.chooseAircraft(aircraft, flightId));
        } catch (AircraftNotFoundException | AircraftAlreadyExistsException | FlightNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteFlight(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(aircraftService.deleteAircraft(id));
        } catch (AircraftNotFoundException e) {
            return ResponseEntity.badRequest().body("USER NOT FOUND");
        }
    }



}
