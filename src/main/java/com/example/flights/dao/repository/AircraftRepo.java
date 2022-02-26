package com.example.flights.dao.repository;

import com.example.flights.domain.entity.AircraftEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface AircraftRepo extends CrudRepository<AircraftEntity, Long> {
    AircraftEntity findByManufacturerAndModel(String manufacturer, String model);

    @Transactional
    @Modifying
    @Query("update AircraftEntity e set e.capacity = ?1, e.manufacturer = ?2, e.model = ?3 where e.id = ?4")
    int updateAircraftById(int capacity, String manufacturer, String model, Long userId);
}
