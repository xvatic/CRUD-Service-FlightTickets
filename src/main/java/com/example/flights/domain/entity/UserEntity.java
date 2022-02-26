package com.example.flights.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    @JsonIgnore
    @ManyToMany(mappedBy = "registeredUsers")
    private List<FlightEntity> orderedFlights;

    public UserEntity() {
        orderedFlights = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setOrderedFlights(List<FlightEntity> orderedFlights) {
        this.orderedFlights = orderedFlights;
    }

    public void addFLight(FlightEntity flight) {
        this.orderedFlights.add(flight);
    }

    public List<FlightEntity> getOrderedFlights() {
        return orderedFlights;
    }
}
