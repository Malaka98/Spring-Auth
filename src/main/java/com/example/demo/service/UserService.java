package com.example.demo.service;

import com.example.demo.dto.UserDTO;

import java.util.List;

public interface UserService {
    void addUser(UserDTO dto);
    UserDTO getUserByUsername(String username);
    List<UserDTO> getUsers();
}
