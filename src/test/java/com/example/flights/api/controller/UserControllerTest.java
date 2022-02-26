package com.example.flights.api.controller;

import com.example.flights.api.dto.UserDTO;
import com.example.flights.business.exception.UserAlreadyExistsException;
import com.example.flights.business.exception.UserNotFoundException;
import com.example.flights.business.service.UserService;
import com.example.flights.dao.repository.UserRepo;
import com.example.flights.domain.entity.UserEntity;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = UserController.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepo userRepo;

    @Test
    void registration() throws Exception {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUsername("Ivan");
        user.setPassword("123");

        BDDMockito.when(userService.registration(BDDMockito.any(UserEntity.class))).thenReturn(UserDTO.fromEntityToDTO(user));

        mockMvc.perform(MockMvcRequestBuilders
                .post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"username\" : \"Ivan\", \"password\" : \"1234\"}")
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("1"));
    }

    @Test
    void login() throws Exception {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUsername("Ivan");
        user.setPassword("123");

        BDDMockito.when(userService.login(BDDMockito.any(UserEntity.class))).thenReturn(Boolean.TRUE);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\" : 1, \"password\" : \"1234\"}")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("SUCCESS"));
    }

    @Test
    void getUser() throws Exception {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUsername("Ivan");
        user.setPassword("123");

        BDDMockito.when(userService.getUser(user.getId())).thenReturn(UserDTO.fromEntityToDTO(user));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users?id=1")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", CoreMatchers.is("Ivan")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)));
    }

    @Test
    void updateUserInformation() throws Exception {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUsername("Ivan");
        user.setPassword("123");

        BDDMockito.when(userService.update(BDDMockito.any(UserEntity.class))).thenReturn(user.getId());

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\" : 1, \"username\" : \"Ivan\", \"password\" : \"1234\"}")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("1"));

    }

    @Test
    void deleteUser() throws Exception {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUsername("Ivan");
        user.setPassword("123");

        BDDMockito.when(userService.deleteUser(user.getId())).thenReturn(user.getId());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/users/1")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("1"));

    }
}