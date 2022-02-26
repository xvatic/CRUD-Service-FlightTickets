package com.example.flights.dao.repository;

import com.example.flights.domain.entity.FlightEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface FlightRepo extends CrudRepository<FlightEntity, Long> {
    @Transactional
    @Modifying
    @Query("update FlightEntity e set e.destination = ?1, e.source = ?2 where e.id = ?3")
    int updateFlightById(String destination, String source, Long flightId);
}
