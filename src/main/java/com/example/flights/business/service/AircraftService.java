package com.example.flights.business.service;

import com.example.flights.domain.entity.AircraftEntity;
import com.example.flights.domain.entity.FlightEntity;
import com.example.flights.business.exception.AircraftAlreadyExistsException;
import com.example.flights.business.exception.AircraftNotFoundException;
import com.example.flights.business.exception.FlightNotFoundException;
import com.example.flights.dao.repository.AircraftRepo;
import com.example.flights.dao.repository.FlightRepo;
import com.example.flights.api.dto.AircraftDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AircraftService {

    @Autowired
    AircraftRepo aircraftRepo;

    @Autowired
    FlightRepo flightRepo;

    public AircraftDTO addAircraft(AircraftEntity aircraft) throws AircraftAlreadyExistsException {
        if (aircraftRepo.findByManufacturerAndModel(aircraft.getManufacturer(), aircraft.getModel()) != null) {
            throw new AircraftAlreadyExistsException();
        } else {
            return AircraftDTO.fromEntityToDTO(aircraftRepo.save(aircraft));
        }
    }

    public AircraftDTO getAircraft(Long id) throws AircraftNotFoundException {
        AircraftEntity aircraft;
        if (aircraftRepo.findById(id).isPresent()) {
            return AircraftDTO.fromEntityToDTO(aircraftRepo.findById(id).get());
        } else {
            throw new AircraftNotFoundException();
        }
    }

    public Long updateAircraft(AircraftEntity aircraft) throws AircraftNotFoundException, AircraftAlreadyExistsException {
        if (aircraftRepo.findById(aircraft.getId()).isPresent()) {
            if (aircraftRepo.findByManufacturerAndModel(aircraft.getManufacturer(),aircraft.getModel()) != null) {
                throw new AircraftAlreadyExistsException();
            } else {
                aircraftRepo.updateAircraftById(aircraft.getCapacity(), aircraft.getManufacturer(), aircraft.getModel(), aircraft.getId());
                return aircraft.getId();
            }

        } else {
            throw new AircraftNotFoundException();
        }

    }

    public FlightEntity chooseAircraft(AircraftEntity aircraft, Long flightId) throws AircraftNotFoundException, AircraftAlreadyExistsException, FlightNotFoundException {
        if (aircraftRepo.findById(aircraft.getId()).isPresent()) {
            if (flightRepo.findById(flightId).isPresent()) {
                FlightEntity flight = flightRepo.findById(flightId).get();
                flight.setAircraft(aircraftRepo.findById(aircraft.getId()).get());
                flightRepo.save(flight);
                return flight;
            } else {
                throw new FlightNotFoundException("FLIGHT NOT FOUND");
            }
        } else {
            throw new AircraftNotFoundException();
        }

    }

    public Long deleteAircraft(Long id) throws AircraftNotFoundException {
        if (aircraftRepo.findById(id).isPresent()) {
            aircraftRepo.deleteById(id);
            return id;
        } else {
            throw new AircraftNotFoundException();
        }
    }
}
