package com.example.flights.api.dto;

import com.example.flights.domain.entity.FlightEntity;
import com.example.flights.domain.entity.UserEntity;
import org.apache.catalina.LifecycleState;

import java.util.List;

public class FlightDTO {
    private String Source;
    private String destination;
    private List<UserEntity> passengerList;
    private Long id;

    public FlightDTO() {
    }

    public static FlightDTO fromEntityToDTO(FlightEntity flightEntity) {
        FlightDTO dto = new FlightDTO();
        dto.setDestination(flightEntity.getDestination());
        dto.setSource(flightEntity.getSource());
        dto.setId(flightEntity.getId());
        dto.setPassengerList(flightEntity.getRegisteredUsers());
        return dto;
    }

    public void setPassengerList(List<UserEntity> passengerList) {
        this.passengerList = passengerList;
    }

    public List<UserEntity> getPassengerList() {
        return passengerList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
