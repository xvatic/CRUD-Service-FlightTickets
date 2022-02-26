package com.example.flights.api.controller;

import com.example.flights.business.exception.WrongPasswordException;
import com.example.flights.domain.entity.UserEntity;
import com.example.flights.business.exception.UserAlreadyExistsException;
import com.example.flights.business.exception.UserNotFoundException;
import com.example.flights.business.service.UserService;
import com.example.flights.api.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity registration(@RequestBody UserEntity user) {
        try {
            UserDTO result = userService.registration(user);
            return ResponseEntity.ok(result.getId());
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("UNKNOWN EXCEPTION");
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserEntity user) {
        try {
            boolean result = userService.login(user);
            return ResponseEntity.ok("SUCCESS");
        } catch (UserNotFoundException | WrongPasswordException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("UNKNOWN EXCEPTION");
        }
    }


    @GetMapping
    public UserDTO getUser(@RequestParam Long id) throws Exception {
        try {
            return userService.getUser(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"UNKNOWN EXCEPTION");
        }
    }

    @PutMapping
    public ResponseEntity updateUserInformation(@RequestBody UserEntity user) {
        try {
            return ResponseEntity.ok(userService.update(user));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.deleteUser(id));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
