package com.example.flights.business.service;

import com.example.flights.business.exception.AircraftAlreadyExistsException;
import com.example.flights.business.exception.AircraftNotFoundException;
import com.example.flights.business.exception.FlightAlreadyExistsException;
import com.example.flights.business.exception.FlightNotFoundException;
import com.example.flights.dao.repository.AircraftRepo;
import com.example.flights.dao.repository.FlightRepo;
import com.example.flights.dao.repository.UserRepo;
import com.example.flights.domain.entity.AircraftEntity;
import com.example.flights.domain.entity.FlightEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = AircraftService.class)
@ExtendWith(MockitoExtension.class)
class AircraftServiceTest {

    @Autowired
    AircraftService aircraftService;

    @MockBean
    private AircraftRepo aircraftRepo;

    @MockBean
    private FlightRepo flightRepo;

    @Test
    void addAircraft() {
        AircraftEntity aircraft = new AircraftEntity();
        aircraft.setId(2L);
        aircraft.setCapacity(100);
        aircraft.setManufacturer("Boeing");
        aircraft.setModel("777");

        BDDMockito.given(aircraftRepo.save(aircraft)).willReturn(aircraft);

        Assertions.assertDoesNotThrow(() -> aircraftService.addAircraft(aircraft));

        Mockito.verify(aircraftRepo, Mockito.atLeastOnce()).save(aircraft);
    }

    @Test
    void addAircraftDuplicate() {
        AircraftEntity aircraft = new AircraftEntity();
        aircraft.setId(2L);
        aircraft.setCapacity(100);
        aircraft.setManufacturer("Boeing");
        aircraft.setModel("777");

        BDDMockito.given(aircraftRepo.save(aircraft)).willReturn(aircraft);
        BDDMockito.given(aircraftRepo.findByManufacturerAndModel(aircraft.getManufacturer(), aircraft.getModel())).willReturn(aircraft);

        Assertions.assertThrows(AircraftAlreadyExistsException.class, () -> aircraftService.addAircraft(aircraft));
    }

    @Test
    void getAircraft() {
        AircraftEntity aircraft = new AircraftEntity();
        aircraft.setId(2L);
        aircraft.setCapacity(100);
        aircraft.setManufacturer("Boeing");
        aircraft.setModel("777");

        BDDMockito.given(aircraftRepo.findById(aircraft.getId())).willReturn(Optional.of(aircraft));

        Assertions.assertDoesNotThrow(() -> aircraftService.getAircraft(aircraft.getId()));
        Mockito.verify(aircraftRepo, Mockito.atLeastOnce()).findById(aircraft.getId());
    }

    @Test
    void updateAircraft() {
        AircraftEntity aircraft = new AircraftEntity();
        aircraft.setId(2L);
        aircraft.setCapacity(100);
        aircraft.setManufacturer("Boeing");
        aircraft.setModel("777");

        BDDMockito.given(aircraftRepo.save(aircraft)).willReturn(aircraft);
        BDDMockito.given(aircraftRepo.findById(aircraft.getId())).willReturn(Optional.of(aircraft));

        Assertions.assertDoesNotThrow(() -> aircraftService.updateAircraft(aircraft));
        Mockito.verify(aircraftRepo, Mockito.atLeastOnce()).updateAircraftById(aircraft.getCapacity(), aircraft.getManufacturer(),aircraft.getModel(), aircraft.getId());
    }

    @Test
    void chooseAircraft() throws AircraftNotFoundException, FlightNotFoundException, AircraftAlreadyExistsException {
        AircraftEntity aircraft = new AircraftEntity();
        aircraft.setId(2L);
        aircraft.setCapacity(100);
        aircraft.setManufacturer("Boeing");
        aircraft.setModel("777");

        FlightEntity flight = new FlightEntity();
        flight.setId(2L);
        flight.setCapacity(100);
        flight.setSource("Praha");
        flight.setDestination("Minsk");

        BDDMockito.given(flightRepo.findById(flight.getId())).willReturn(Optional.of(flight));
        BDDMockito.given(aircraftRepo.findById(aircraft.getId())).willReturn(Optional.of(aircraft));
        BDDMockito.given(flightRepo.save(flight)).willReturn(flight);
        BDDMockito.given(aircraftRepo.save(aircraft)).willReturn(aircraft);

        FlightEntity result = aircraftService.chooseAircraft(aircraft, flight.getId());

        Assertions.assertEquals(result.getAircraft(), aircraft);
    }

    @Test
    void deleteAircraft() throws AircraftNotFoundException {
        AircraftEntity aircraft = new AircraftEntity();
        aircraft.setId(2L);
        aircraft.setCapacity(100);
        aircraft.setManufacturer("Boeing");
        aircraft.setModel("777");

        BDDMockito.given(aircraftRepo.findById(aircraft.getId())).willReturn(Optional.of(aircraft));
        aircraftService.deleteAircraft(aircraft.getId());
        Mockito.verify(aircraftRepo, Mockito.atLeastOnce()).deleteById(aircraft.getId());
    }
}