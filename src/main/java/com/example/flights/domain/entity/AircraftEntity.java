package com.example.flights.domain.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class AircraftEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int capacity;
    private String manufacturer;
    private String model;

    @OneToMany(mappedBy = "aircraft")
    private List<FlightEntity> operatingFlights;

    public AircraftEntity() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void addFlight(FlightEntity flight) {
        operatingFlights.add(flight);
    }
}
