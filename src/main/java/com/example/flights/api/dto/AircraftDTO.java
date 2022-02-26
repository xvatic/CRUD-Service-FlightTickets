package com.example.flights.api.dto;

import com.example.flights.domain.entity.AircraftEntity;

public class AircraftDTO {
    private String Manufacturer;
    private String Model;

    public AircraftDTO() {
    }

    public static AircraftDTO fromEntityToDTO(AircraftEntity aircraftEntity) {
        AircraftDTO dto = new AircraftDTO();
        dto.setManufacturer(aircraftEntity.getManufacturer());
        dto.setModel(aircraftEntity.getModel());
        return dto;
    }

    public String getManufacturer() {
        return Manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        Manufacturer = manufacturer;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }
}
