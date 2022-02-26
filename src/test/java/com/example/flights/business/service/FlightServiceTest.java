package com.example.flights.business.service;

import com.example.flights.api.dto.FlightDTO;
import com.example.flights.business.exception.FlightAlreadyExistsException;
import com.example.flights.business.exception.FlightNotFoundException;
import com.example.flights.business.exception.UserAlreadyExistsException;
import com.example.flights.business.exception.UserNotFoundException;
import com.example.flights.dao.repository.FlightRepo;
import com.example.flights.dao.repository.UserRepo;
import com.example.flights.domain.entity.FlightEntity;
import com.example.flights.domain.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = FlightService.class)
@ExtendWith(MockitoExtension.class)
class FlightServiceTest {

    @Autowired
    FlightService flightService;

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private FlightRepo flightRepo;

    @Test
    void addFlight() {
        FlightEntity flight = new FlightEntity();
        flight.setId(2L);
        flight.setCapacity(100);
        flight.setSource("Praha");
        flight.setDestination("Minsk");

        BDDMockito.given(flightRepo.save(flight)).willReturn(flight);

        Assertions.assertDoesNotThrow(() -> flightService.addFlight(flight));

        Mockito.verify(flightRepo, Mockito.atLeastOnce()).save(flight);
    }

    @Test
    void addDuplicateFlight() {
        FlightEntity flight = new FlightEntity();
        flight.setId(2L);
        flight.setCapacity(100);
        flight.setSource("Praha");
        flight.setDestination("Minsk");

        BDDMockito.given(flightRepo.save(flight)).willReturn(flight);
        BDDMockito.given(flightRepo.findById(flight.getId())).willReturn(Optional.of(flight));

        Assertions.assertThrows(FlightAlreadyExistsException.class, () -> flightService.addFlight(flight));
    }

    @Test
    void updateFlight() {
        FlightEntity flight = new FlightEntity();
        flight.setId(2L);
        flight.setCapacity(100);
        flight.setSource("Praha");
        flight.setDestination("Minsk");

        BDDMockito.given(flightRepo.save(flight)).willReturn(flight);
        BDDMockito.given(flightRepo.findById(flight.getId())).willReturn(Optional.of(flight));

        Assertions.assertDoesNotThrow(() -> flightService.updateFlight(flight));
        Mockito.verify(flightRepo, Mockito.atLeastOnce()).updateFlightById(flight.getDestination(), flight.getSource(), flight.getId());
    }

    @Test
    void getFlight() {
        FlightEntity flight = new FlightEntity();
        flight.setId(2L);
        flight.setCapacity(100);
        flight.setSource("Praha");
        flight.setDestination("Minsk");

        BDDMockito.given(flightRepo.findById(flight.getId())).willReturn(Optional.of(flight));

        Assertions.assertDoesNotThrow(() -> flightService.getFlight(flight.getId()));
        Mockito.verify(flightRepo, Mockito.atLeastOnce()).findById(flight.getId());
    }


    @Test
    void deleteFlight() throws FlightNotFoundException {
        FlightEntity flight = new FlightEntity();
        flight.setId(2L);
        flight.setCapacity(100);
        flight.setSource("Praha");
        flight.setDestination("Minsk");

        BDDMockito.given(flightRepo.findById(flight.getId())).willReturn(Optional.of(flight));
        flightService.deleteFlight(flight.getId());
        Mockito.verify(flightRepo, Mockito.atLeastOnce()).deleteById(flight.getId());
    }

    @Test
    void getAllFlights() {
        FlightEntity flight = new FlightEntity();
        flight.setId(2L);
        flight.setCapacity(100);
        flight.setSource("Praha");
        flight.setDestination("Minsk");

        FlightEntity flight1 = new FlightEntity();
        flight1.setId(3L);
        flight1.setCapacity(50);
        flight1.setSource("London");
        flight1.setDestination("Dubai");

        List<FlightEntity> flights = new ArrayList<>();
        flights.add(flight);
        flights.add(flight1);
        Page<FlightEntity> pagedFlights = new PageImpl(flights);
        BDDMockito.given(flightRepo.findAll()).willReturn(pagedFlights);

        List<FlightDTO> res = flightService.getAllFlights();

        Assertions.assertEquals(res.get(0).getId(), flight.getId());
        Assertions.assertEquals(res.get(1).getId(), flight1.getId());
    }

    @Test
    void registerUserToFlight() throws UserNotFoundException, FlightNotFoundException {
        FlightEntity flight = new FlightEntity();
        flight.setId(2L);
        flight.setCapacity(100);
        flight.setSource("Praha");
        flight.setDestination("Minsk");

        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUsername("Ivan");
        user.setPassword("123");

        List<UserEntity> passengerList = new LinkedList<UserEntity>(Collections.singletonList(user));
        List<FlightEntity> flightsList = new LinkedList<FlightEntity>(Collections.singletonList(flight));

        BDDMockito.given(flightRepo.findById(flight.getId())).willReturn(Optional.of(flight));
        BDDMockito.given(userRepo.findById(user.getId())).willReturn(Optional.of(user));
        BDDMockito.given(flightRepo.save(flight)).willReturn(flight);
        BDDMockito.given(userRepo.save(user)).willReturn(user);

        FlightDTO flightDTO = flightService.registerUserToFlight(user, flight.getId());

        user.setOrderedFlights(flightsList);
        flight.setRegisteredUsers(passengerList);

        Assertions.assertEquals(FlightDTO.fromEntityToDTO(flight).getPassengerList(), flightDTO.getPassengerList());
    }
}