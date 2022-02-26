package com.example.flights.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class FlightEntity {
    @Id
    private Long id;
    private int capacity;
    private String source;
    private String destination;

    @ManyToOne
    @JoinColumn(name = "aircraft_id", referencedColumnName = "id")
    private AircraftEntity aircraft;

    @JsonIgnore
    @ManyToMany
    @JoinTable (
            name = "users_registered",
            joinColumns = @JoinColumn(name = "flight_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<UserEntity> registeredUsers;

    public FlightEntity() {
        registeredUsers = new ArrayList<>();
    }



    public void setId(Long id) {
        this.id = id;
    }

    public void setRegisteredUsers(List<UserEntity> registeredUsers) {
        this.registeredUsers = registeredUsers;
    }

    public AircraftEntity getAircraft() {
        return aircraft;
    }

    public void setAircraft(AircraftEntity aircraft) {
        this.aircraft = aircraft;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Long getId() {
        return id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void registerPassenger(UserEntity passenger) {
        registeredUsers.add(passenger);
        capacity--;
    }

    public List<UserEntity> getRegisteredUsers() {
        return registeredUsers;
    }
}
