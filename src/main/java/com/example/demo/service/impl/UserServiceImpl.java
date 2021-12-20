package com.example.demo.service.impl;

import com.example.demo.dto.SuperDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.util.ModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    // private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private void validateUserInfo(UserDTO dto) {
        StringBuilder errors = new StringBuilder();
        if(dto.getUsername().trim().length() < 6) {
            errors.append("Username must have more than six characters\n");
        }

        if(dto.getPassword().length() < 8) {
            errors.append("Password must have more than eight characters\n");
        }

        if(errors.length() > 0) {
            throw new BadRequestException(errors.toString());
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void addUser(UserDTO dto) {
        validateUserInfo(dto);
        if(repository.findUserByUsername(dto.getUsername()).isPresent())
            throw new BadRequestException("Username already exists");
       // dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        repository.save((User) ModelConverter.dtoToModel(dto));
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        Optional<User> userOptional = repository.findUserByUsername(username);
        if(userOptional.isPresent()) {
            return (UserDTO) ModelConverter.modelToDto(userOptional.get());
        }
        throw new NotFoundException(String.format("User : %s not found\n", username));
    }

    @Override
    public List<UserDTO> getUsers() {
        List<SuperDTO> userRet = ModelConverter.modelsToDtoList(repository.findAll());
        List<UserDTO> dtoList = new ArrayList<>();
        for(SuperDTO dto : userRet) {
            dtoList.add((UserDTO) dto);
        }
        return dtoList;
    }

}
