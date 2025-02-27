package com.blogging.bloggingApp.services;

import com.blogging.bloggingApp.payloads.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    // we will not be using entity class directly into our service class
    //instead we will use DTO classes from payloads
    //DTO stands for data transfer objects

    UserDto createUser(UserDto user);
    UserDto updateUser(UserDto user,Integer userId);
    UserDto getUserById(Integer userId);
    List<UserDto> getAllUsers();
    boolean deleteUser(Integer userId);

}
