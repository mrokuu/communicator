package com.app.controller;

import java.util.HashSet;
import java.util.List;

import com.app.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.controller.mapper.UserMapper;
import com.app.dto.UserDto;
import com.app.exception.UserException;
import com.app.modal.User;
import com.app.common.request.UpdateUserRequest;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
	
	private UserService userService;
	
	
	@PutMapping("/update/{userId}")
	public ResponseEntity<UserDto> updateUserHandler(@RequestBody UpdateUserRequest req, @PathVariable Long userId) throws UserException{
		User updatedUser=userService.updateUser(userId, req);
		UserDto userDto= UserMapper.toUserDTO(updatedUser);

		return new ResponseEntity<UserDto>(userDto,HttpStatus.OK);
	}
	
	@GetMapping("/profile")
	public ResponseEntity<UserDto> getUserProfileHandler(@RequestHeader("Authorization")String jwt){

		User user=userService.findUserProfile(jwt);
		
		UserDto userDto= UserMapper.toUserDTO(user);
		

		return new ResponseEntity<UserDto>(userDto,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/search")
    public ResponseEntity<HashSet<UserDto>> searchUsersByName(@RequestParam("name") String name) {
		

        List<User> users=userService.searchUser(name);
        
        HashSet<User> set=new HashSet<>(users);
        
        HashSet<UserDto> userDtos= UserMapper.toUserDtos(set);
        

		return new ResponseEntity<>(userDtos,HttpStatus.ACCEPTED);
    }

}
