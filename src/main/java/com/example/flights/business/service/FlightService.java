package com.example.flights.business.service;

import com.example.flights.domain.entity.FlightEntity;
import com.example.flights.domain.entity.UserEntity;
import com.example.flights.business.exception.FlightAlreadyExistsException;
import com.example.flights.business.exception.FlightNotFoundException;
import com.example.flights.business.exception.UserNotFoundException;
import com.example.flights.dao.repository.FlightRepo;
import com.example.flights.dao.repository.UserRepo;
import com.example.flights.api.dto.FlightDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class FlightService {

    @Autowired
    private FlightRepo flightRepo;

    @Autowired
    private UserRepo userRepo;

    public FlightDTO addFlight(FlightEntity flight) throws FlightAlreadyExistsException {

        if (flightRepo.findById(flight.getId()).isPresent()) {
            throw new FlightAlreadyExistsException("FLIGHT ALREADY EXISTS");
        } else {
            return FlightDTO.fromEntityToDTO(flightRepo.save(flight));
        }
    }

    public Long updateFlight(FlightEntity flight) throws FlightNotFoundException {
        if (flightRepo.findById(flight.getId()).isPresent()) {
            flightRepo.updateFlightById(flight.getDestination(),flight.getSource(),flight.getId());
            return flight.getId();
        } else {
            throw new FlightNotFoundException("FLIGHT NOT FOUND");
        }

    }


    public FlightDTO getFlight(Long id) throws Exception {
        FlightEntity flight;
        if (flightRepo.findById(id).isPresent()) {
            return FlightDTO.fromEntityToDTO(flightRepo.findById(id).get());
        } else {
            throw new FlightNotFoundException("FLIGHT NOT FOUND");
        }
    }


    public Long deleteFlight(Long id) throws FlightNotFoundException {
        if (flightRepo.findById(id).isPresent()) {
            flightRepo.deleteById(id);
            return id;
        } else {
            throw new FlightNotFoundException("FLIGHT NOT FOUND");
        }
    }

    public List<FlightDTO> getAllFlights() {
        Iterable<FlightEntity> iter = flightRepo.findAll();
        List<FlightDTO> flights = StreamSupport.stream(iter.spliterator(), false).map(flight -> {
            FlightDTO dto = new FlightDTO();
            BeanUtils.copyProperties(flight, dto);
            return dto;
        }).collect(Collectors.toList());

        return flights;
    }

    public FlightDTO registerUserToFlight(UserEntity user, Long flightId) throws UserNotFoundException, FlightNotFoundException {
        if (flightRepo.findById(flightId).isPresent()) {
            if (userRepo.findById(user.getId()).isPresent()) {
                UserEntity requiredUser = userRepo.findById(user.getId()).get();
                FlightEntity flight = flightRepo.findById(flightId).get();
                flight.registerPassenger(requiredUser);
                requiredUser.addFLight(flight);
                return FlightDTO.fromEntityToDTO(flightRepo.save(flight));
            } else {
                throw new UserNotFoundException("USER NOT FOUND");
            }
        } else {
            throw new FlightNotFoundException("FLIGHT NOT FOUND");
        }
    }
}
