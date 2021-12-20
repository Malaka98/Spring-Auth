package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.exception.BadRequestException;

import java.util.List;

public interface UserService {
    void addUser(UserDTO dto) throws BadRequestException;
    UserDTO getUserByUsername(String username);
    List<UserDTO> getUsers();
}
