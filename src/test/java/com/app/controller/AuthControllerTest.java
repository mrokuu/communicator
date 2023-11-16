package com.app.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.app.config.JwtTokenProvider;
import com.app.exception.UserException;
import com.app.modal.User;
import com.app.repository.UserRepository;
import com.app.service.CustomUserDetailsService;
import com.app.common.request.LoginRequest;
import com.app.common.response.AuthResponse;

import java.util.Optional;

import static javax.security.auth.callback.ConfirmationCallback.OK;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @InjectMocks
    private AuthController authController;

    private User user;
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setFull_name("Test User");

        loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password");
    }


    @Test
    void createUserHandler_EmailExists() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User()));

        assertThrows(UserException.class, () -> authController.createUserHandler(user));
    }



}
