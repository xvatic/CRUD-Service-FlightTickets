package com.example.flights.business.service;

import com.example.flights.api.dto.UserDTO;
import com.example.flights.business.exception.UserAlreadyExistsException;
import com.example.flights.business.exception.UserNotFoundException;
import com.example.flights.dao.repository.FlightRepo;
import com.example.flights.dao.repository.UserRepo;
import com.example.flights.domain.entity.FlightEntity;
import com.example.flights.domain.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = UserService.class)
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Autowired
    UserService userService;

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private FlightRepo flightRepo;

    @Test
    void registration() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUsername("Ivan");
        user.setPassword("123");

        BDDMockito.given(userRepo.save(user)).willReturn(user);

        Assertions.assertDoesNotThrow(() -> userService.registration(user));

        Mockito.verify(userRepo, Mockito.atLeastOnce()).save(user);
    }

    @Test
    void registrationDuplicate() throws Exception {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUsername("Ivan");
        user.setPassword("123");

        BDDMockito.given(userRepo.save(user)).willReturn(user);
        BDDMockito.given(userRepo.findByUsername(user.getUsername())).willReturn(user);

        Assertions.assertThrows(UserAlreadyExistsException.class, () -> userService.registration(user));
    }

    @Test
    void login() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUsername("Ivan");
        user.setPassword("123");

        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        user1.setPassword("123");

        BDDMockito.given(userRepo.save(user)).willReturn(user);
        BDDMockito.given(userRepo.findById(user.getId())).willReturn(Optional.of(user));

        Assertions.assertDoesNotThrow(() -> userService.login(user1));

    }

    @Test
    void update() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUsername("Ivan");
        user.setPassword("123");

        BDDMockito.given(userRepo.save(user)).willReturn(user);
        BDDMockito.given(userRepo.findById(user.getId())).willReturn(Optional.of(user));

        Assertions.assertDoesNotThrow(() -> userService.update(user));
        Mockito.verify(userRepo, Mockito.atLeastOnce()).updateUserById(user.getUsername(), user.getPassword(), user.getId());
    }

    @Test
    void getUser() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUsername("Ivan");
        user.setPassword("123");

        BDDMockito.given(userRepo.findById(user.getId())).willReturn(Optional.of(user));

        Assertions.assertDoesNotThrow(() -> userService.getUser(user.getId()));
        Mockito.verify(userRepo, Mockito.atLeastOnce()).findById(user.getId());
    }

    @Test
    void deleteUser() throws UserNotFoundException {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUsername("Ivan");
        user.setPassword("123");
        BDDMockito.given(userRepo.findById(user.getId())).willReturn(Optional.of(user));
        userService.deleteUser(user.getId());
        Mockito.verify(userRepo, Mockito.atLeastOnce()).deleteById(user.getId());
    }

}