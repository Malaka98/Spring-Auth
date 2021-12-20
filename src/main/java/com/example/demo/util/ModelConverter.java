package com.example.demo.util;

import com.example.demo.dto.SuperDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.exception.UnknownException;
import com.example.demo.model.SuperModel;
import com.example.demo.model.User;

import java.util.ArrayList;
import java.util.List;

public class ModelConverter {

    public static SuperDTO modelToDto(SuperModel model) {
        if(model instanceof User) {
            User user = (User) model;
            return new UserDTO(user.getId(), user.getUsername(), user.getFirstName(), user.getLastName());
        } else {
            throw new UnknownException(String.format("Unknown : %s model\n", model.getClass().getName()));
        }
    }

    public static List<SuperDTO> modelsToDtoList(List<User> models) {
        List<SuperDTO> dtoList = new ArrayList<>();
        for(SuperModel model : models) {
            dtoList.add(modelToDto(model));
        }
        return dtoList;
    }

    public static SuperModel dtoToModel(SuperDTO dto) {
        if(dto instanceof UserDTO) {
            UserDTO userDTO = (UserDTO) dto;
            return new User(userDTO.getUsername(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getPassword());
        }  else {
            throw new UnknownException(String.format("Unknown : %s dto\n", dto.getClass().getName()));
        }
    }

    public static List<SuperModel> dtoListToModels(List<SuperDTO> dtoList) {
        List<SuperModel> models = new ArrayList<>();
        for(SuperDTO dto : dtoList) {
            models.add(dtoToModel(dto));
        }
        return models;
    }
}
