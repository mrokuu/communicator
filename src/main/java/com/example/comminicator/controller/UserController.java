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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);


    private final UserService userService;

    @PutMapping("/update/{userId}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UpdateUserRequest req, @PathVariable Integer userId) throws UserException {
        logger.info("Updating user with ID {}", userId);
        User updatedUser = userService.updateUser(userId, req);
        UserDto userDto= UserDtoMapper.toUserDTO(updatedUser);

        return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
    }


    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUser(@RequestHeader("Authorization") String jwt) {
        logger.info("Requesting user profile");
        User user = userService.findUserProfile(jwt);
        logger.info("Profile requested for user {}", user.getEmail());
        UserDto userDto=UserDtoMapper.toUserDTO(user);

        return new ResponseEntity<UserDto>(userDto,HttpStatus.ACCEPTED);
    }

    @GetMapping("/search")
    public ResponseEntity<HashSet<UserDto>> searchUsersByName(@RequestParam("name") String name) {
        logger.info("Searching for users with name {}", name);
        List<User> users = userService.searchUser(name);
        HashSet<User> UserSet = new HashSet<>(users);
        HashSet<UserDto> userDtos=UserDtoMapper.toUserDtos(UserSet);
        return new ResponseEntity<>(userDtos,HttpStatus.ACCEPTED);    }
}
