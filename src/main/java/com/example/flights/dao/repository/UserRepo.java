package com.example.flights.dao.repository;

import com.example.flights.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepo extends CrudRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);

    @Transactional
    @Modifying
    @Query("update UserEntity e set e.username = ?1, e.password = ?2 where e.id = ?3")
    int updateUserById(String username, String password, Long userId);

}
