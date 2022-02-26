package com.example.flights.business.service;

import com.example.flights.business.exception.WrongPasswordException;
import com.example.flights.domain.entity.UserEntity;
import com.example.flights.business.exception.UserAlreadyExistsException;
import com.example.flights.business.exception.UserNotFoundException;
import com.example.flights.dao.repository.UserRepo;
import com.example.flights.api.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public UserDTO registration(UserEntity user) throws UserAlreadyExistsException {

        if (userRepo.findByUsername(user.getUsername()) != null) {
            throw new UserAlreadyExistsException("USER ALREADY EXISTS");
        } else {
            return UserDTO.fromEntityToDTO(userRepo.save(user));
        }

    }

    public boolean login(UserEntity user) throws UserNotFoundException, WrongPasswordException {

        if (userRepo.findById(user.getId()).isEmpty()) {
            throw new UserNotFoundException("USER NOT FOUND");
        } else {
            if (Objects.equals(userRepo.findById(user.getId()).get().getPassword(), user.getPassword()))
                return true;
            else
                throw new WrongPasswordException("WRONG PASSWORD");
        }
    }

    public Long update(UserEntity user) throws UserNotFoundException {
        if (userRepo.findById(user.getId()).isPresent()) {
            userRepo.updateUserById(user.getUsername(),user.getPassword(),user.getId());
            return user.getId();
        } else {
            throw new UserNotFoundException("USER NOT FOUND");
        }

    }


    public UserDTO getUser(Long id) throws Exception {
        UserEntity user;
        if (userRepo.findById(id).isPresent()) {
            return UserDTO.fromEntityToDTO(userRepo.findById(id).get());
        } else {
            throw new Exception("UNKNOWN EXCEPTION");
        }
    }


    public Long deleteUser(Long id) throws UserNotFoundException {
        if (userRepo.findById(id).isPresent()) {
            userRepo.deleteById(id);
            return id;
        } else {
            throw new UserNotFoundException("USER NOT FOUND");
        }
    }
}
