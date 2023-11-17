package com.app.controller.mapper;

import java.util.HashSet;
import java.util.Set;

import com.app.dto.UserDto;
import com.app.modal.User;

public class UserMapper {

	
	public static UserDto toUserDTO(User user) {
		
		UserDto userDto=new UserDto();
		
		userDto.setEmail(user.getEmail());
		userDto.setFullName(user.getFullName());
		userDto.setId(user.getId());
		userDto.setProfile_picture(user.getProfile_picture());
		
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
