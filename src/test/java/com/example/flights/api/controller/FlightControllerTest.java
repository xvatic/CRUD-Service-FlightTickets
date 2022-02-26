package com.example.flights.api.controller;

import com.example.flights.api.dto.FlightDTO;
import com.example.flights.api.dto.UserDTO;
import com.example.flights.business.exception.FlightAlreadyExistsException;
import com.example.flights.business.exception.FlightNotFoundException;
import com.example.flights.business.service.FlightService;
import com.example.flights.business.service.UserService;
import com.example.flights.dao.repository.UserRepo;
import com.example.flights.domain.entity.FlightEntity;
import com.example.flights.domain.entity.UserEntity;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = FlightController.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class FlightControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FlightService flightService;

    @MockBean
    private UserRepo userRepo;

    @Test
    void addFlight() throws Exception {
        FlightEntity flight = new FlightEntity();
        flight.setId(2L);
        flight.setCapacity(100);
        flight.setSource("Praha");
        flight.setDestination("Minsk");


        BDDMockito.when(flightService.addFlight(BDDMockito.any(FlightEntity.class))).thenReturn(FlightDTO.fromEntityToDTO(flight));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/flights")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\" : 2, \"capacity\" : 100, \"source\" : \"Praha\", \"destination\" : \"Minsk\" }")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("SAVED"));

    }

    @Test
    void getFlight() throws Exception {
        FlightEntity flight = new FlightEntity();
        flight.setId(2L);
        flight.setCapacity(100);
        flight.setSource("Praha");
        flight.setDestination("Minsk");

        BDDMockito.when(flightService.getFlight(flight.getId())).thenReturn(FlightDTO.fromEntityToDTO(flight));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/flights?id=2")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.source", CoreMatchers.is("Praha")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.destination", CoreMatchers.is("Minsk")));
    }

    @Test
    void getAllFligths() throws Exception {
        FlightDTO flight = new FlightDTO();
        flight.setId(2L);
        flight.setSource("Praha");
        flight.setDestination("Minsk");

        FlightDTO flight1 = new FlightDTO();
        flight1.setId(3L);
        flight1.setSource("London");
        flight1.setDestination("Dubai");

        List<FlightDTO> flights = new ArrayList<>();
        flights.add(flight);
        flights.add(flight1);

        BDDMockito.when(flightService.getAllFlights()).thenReturn(flights);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/flights/all")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id", CoreMatchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].id", CoreMatchers.is(3)));
    }

    @Test
    void updateFlightInformation() throws Exception {
        FlightEntity flight = new FlightEntity();
        flight.setId(2L);
        flight.setCapacity(100);
        flight.setSource("Praha");
        flight.setDestination("Minsk");

        BDDMockito.when(flightService.updateFlight(BDDMockito.any(FlightEntity.class))).thenReturn(flight.getId());

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/flights")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\" : 2, \"capacity\" : 100, \"source\" : \"Praha\", \"destination\" : \"Minsk\" }")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("2"));

    }

    @Test
    void registerUserToFlight() throws Exception {
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

        flight.setRegisteredUsers(passengerList);

        BDDMockito.when(flightService.registerUserToFlight(BDDMockito.any(UserEntity.class), BDDMockito.eq(2L))).thenReturn(FlightDTO.fromEntityToDTO(flight));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/flights/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\" : 1}")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.source", CoreMatchers.is("Praha")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.destination", CoreMatchers.is("Minsk")));
    }

    @Test
    void deleteFlight() throws Exception {
        FlightEntity flight = new FlightEntity();
        flight.setId(2L);
        flight.setCapacity(100);
        flight.setSource("Praha");
        flight.setDestination("Minsk");

        BDDMockito.when(flightService.deleteFlight(BDDMockito.eq(2L))).thenReturn(2L);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/flights/2")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("2"));

    }
}