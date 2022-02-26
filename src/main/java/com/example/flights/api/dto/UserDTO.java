package com.example.flights.api.dto;

import com.example.flights.domain.entity.FlightEntity;
import com.example.flights.domain.entity.UserEntity;

import java.util.List;

public class UserDTO {
    private Long id;
    private String username;
    private List<FlightEntity> flightsList;

    public UserDTO() {
    }

    public static UserDTO fromEntityToDTO(UserEntity userEntity) {
        UserDTO model = new UserDTO();
        model.setId(userEntity.getId());
        model.setUsername(userEntity.getUsername());
        model.setFlightsList(userEntity.getOrderedFlights());
        return model;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFlightsList(List<FlightEntity> flightsList) {
        this.flightsList = flightsList;
    }

    public List<FlightEntity> getFlightsList() {
        return flightsList;
    }
}
