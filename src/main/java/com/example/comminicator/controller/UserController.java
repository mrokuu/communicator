package com.example.comminicator.controller;

import com.example.comminicator.controller.mapper.UserDtoMapper;
import com.example.comminicator.dto.UserDto;
import com.example.comminicator.exception.UserException;
import com.example.comminicator.model.User;
import com.example.comminicator.common.request.UpdateUserRequest;
import com.example.comminicator.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;


    @PutMapping("/update/{userId}")
    public ResponseEntity<UserDto> updateUserHandler(@RequestBody UpdateUserRequest req, @PathVariable Integer userId) throws UserException{
        User updatedUser=userService.updateUser(userId, req);
        UserDto userDto=UserDtoMapper.toUserDTO(updatedUser);

        return new ResponseEntity<UserDto>(userDto,HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserProfileHandler(@RequestHeader("Authorization")String jwt){
        User user=userService.findUserProfile(jwt);

        UserDto userDto=UserDtoMapper.toUserDTO(user);

        return new ResponseEntity<UserDto>(userDto,HttpStatus.ACCEPTED);
    }

    @GetMapping("/search")
    public ResponseEntity<HashSet<UserDto>> searchUsersByName(@RequestParam("name") String name) {

        List<User> users=userService.searchUser(name);

        HashSet<User> set=new HashSet<>(users);

        HashSet<UserDto> userDtos=UserDtoMapper.toUserDtos(set);

        return new ResponseEntity<>(userDtos,HttpStatus.ACCEPTED);
    }

}