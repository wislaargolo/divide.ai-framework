package com.ufrn.imd.divide.ai.framework.mapper;

import com.ufrn.imd.divide.ai.framework.dto.request.UserCreateRequestDTO;
import com.ufrn.imd.divide.ai.framework.dto.request.UserUpdateRequestDTO;
import com.ufrn.imd.divide.ai.framework.dto.response.UserResponseDTO;
import com.ufrn.imd.divide.ai.framework.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public User toEntity(UserCreateRequestDTO userCreateRequestDTO) {
        return mapToUser(userCreateRequestDTO.firstName(), userCreateRequestDTO.lastName(), userCreateRequestDTO.email(), userCreateRequestDTO.password(), userCreateRequestDTO.phoneNumber());
    }

    public User toEntity(UserUpdateRequestDTO userUpdateRequestDTO) {
        return mapToUser(userUpdateRequestDTO.firstName(), userUpdateRequestDTO.lastName(), userUpdateRequestDTO.email(), userUpdateRequestDTO.password(), userUpdateRequestDTO.phoneNumber());
    }

    public UserResponseDTO toDto(User user) {
        if (user == null) {
            return null;
        }

        return new UserResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber()
        );
    }

    public List<UserResponseDTO> toDtoList(List<User> users) {
        if (users == null) {
            return null;
        }

        return users.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private User mapToUser(String firstName, String lastName, String email,
                           String password, String phoneNumber) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhoneNumber(phoneNumber);
        return user;
    }
}
