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
}
