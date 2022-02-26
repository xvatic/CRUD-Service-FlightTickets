package com.example.flights.api.controller;

import com.example.flights.api.dto.AircraftDTO;
import com.example.flights.api.dto.FlightDTO;
import com.example.flights.business.exception.AircraftAlreadyExistsException;
import com.example.flights.business.exception.AircraftNotFoundException;
import com.example.flights.business.service.AircraftService;
import com.example.flights.business.service.FlightService;
import com.example.flights.dao.repository.UserRepo;
import com.example.flights.domain.entity.AircraftEntity;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = AircraftController.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class AircraftControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AircraftService aircraftService;

    @MockBean
    private UserRepo userRepo;

    @Test
    void addAircraft() throws Exception {
        AircraftEntity aircraft = new AircraftEntity();
        aircraft.setId(1L);
        aircraft.setCapacity(100);
        aircraft.setManufacturer("Boeing");
        aircraft.setModel("777");

        BDDMockito.when(aircraftService.addAircraft(BDDMockito.any(AircraftEntity.class))).thenReturn(AircraftDTO.fromEntityToDTO(aircraft));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/aircraft")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\" : 1, \"manufacturer\" : \"Boeing\", \"model\" : \"777\" }")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("SAVED"));

    }

    @Test
    void getFlight() throws Exception {
        AircraftEntity aircraft = new AircraftEntity();
        aircraft.setId(1L);
        aircraft.setCapacity(100);
        aircraft.setManufacturer("Boeing");
        aircraft.setModel("777");

        BDDMockito.when(aircraftService.getAircraft(aircraft.getId())).thenReturn(AircraftDTO.fromEntityToDTO(aircraft));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/aircraft?id=1")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.manufacturer", CoreMatchers.is("Boeing")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model", CoreMatchers.is("777")));

    }

    @Test
    void updateAircraftInformation() throws Exception {
        AircraftEntity aircraft = new AircraftEntity();
        aircraft.setId(1L);
        aircraft.setCapacity(100);
        aircraft.setManufacturer("Boeing");
        aircraft.setModel("777");

        BDDMockito.when(aircraftService.updateAircraft(BDDMockito.any(AircraftEntity.class))).thenReturn(aircraft.getId());

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/aircraft")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\" : 1, \"capacity\" : 100, \"manufacturer\" : \"Boeing\", \"model\" : \"777\" }")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("1"));

    }

    @Test
    void chooseAircraftToFligt() throws Exception {
        FlightEntity flight = new FlightEntity();
        flight.setId(2L);
        flight.setCapacity(100);
        flight.setSource("Praha");
        flight.setDestination("Minsk");

        AircraftEntity aircraft = new AircraftEntity();
        aircraft.setId(1L);
        aircraft.setCapacity(100);
        aircraft.setManufacturer("Boeing");
        aircraft.setModel("777");

        BDDMockito.when(aircraftService.chooseAircraft(BDDMockito.any(AircraftEntity.class), BDDMockito.eq(2L))).thenReturn(flight);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/aircraft/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\" : 1}")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.source", CoreMatchers.is("Praha")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.destination", CoreMatchers.is("Minsk")));

    }

    @Test
    void deleteFlight() throws Exception {
        AircraftEntity aircraft = new AircraftEntity();
        aircraft.setId(1L);
        aircraft.setCapacity(100);
        aircraft.setManufacturer("Boeing");
        aircraft.setModel("777");

        BDDMockito.when(aircraftService.deleteAircraft(aircraft.getId())).thenReturn(aircraft.getId());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/aircraft/1")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("1"));

    }
}