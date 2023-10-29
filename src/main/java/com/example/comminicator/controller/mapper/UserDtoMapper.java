package com.example.comminicator.controller.mapper;

import com.example.comminicator.dto.UserDto;
import com.example.comminicator.model.User;

import java.util.HashSet;
import java.util.Set;

public class UserDtoMapper {

    public static UserDto toUserDTO(User user) {

        UserDto userDto=new UserDto();

        userDto.setEmail(user.getEmail());
        userDto.setFullName(user.getFullName());
        userDto.setId(user.getId());
        userDto.setProfilePicture(user.getProfilePicture());

        return userDto;

    }

    public static HashSet<UserDto> toUserDtos(Set<User> set){
        HashSet<UserDto> userDtos=new HashSet<>();

        for(User user:set) {
            UserDto userDto=toUserDTO(user);
            userDtos.add(userDto);
        }

        return userDtos;
    }
}
