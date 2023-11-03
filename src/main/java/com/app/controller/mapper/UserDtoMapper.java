package com.app.controller.mapper;

import java.util.HashSet;
import java.util.Set;

import com.app.dto.UserDto;
import com.app.modal.User;

public class UserDtoMapper {

	
	public static UserDto toUserDTO(User user) {
		
		UserDto userDto=new UserDto();
		
		userDto.setEmail(user.getEmail());
		userDto.setFull_name(user.getFull_name());
		userDto.setId(user.getId());
		userDto.setProfile_pic(user.getProfile_picture());
		
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
