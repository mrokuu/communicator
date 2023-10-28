package com.example.comminicator.controller;

import com.example.comminicator.exception.UserException;
import com.example.comminicator.model.User;
import com.example.comminicator.response.AuthResponse;
import com.example.comminicator.service.AuthService;
import jakarta.validation.Valid;
import jdk.jshell.spi.ExecutionControl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public AuthResponse createUserHandler(@Valid @RequestBody User user) throws ExecutionControl.UserException, UserException {
        return authService.createUserHandler(user);
    }

    
}
