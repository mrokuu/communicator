package com.app.service;

import com.app.config.JwtTokenProvider;
import com.app.exception.UserException;
import com.app.modal.User;
import com.app.repository.UserRepository;
import com.app.request.UpdateUserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserServiceTest {
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(passwordEncoder, userRepository, jwtTokenProvider);
    }


    @Test
    public void testUpdateUser() throws UserException {
        Integer userId = 1;
        UpdateUserRequest req = new UpdateUserRequest();
        req.setFull_name("New Name");
        req.setProfile_picture("New Picture");

        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        User updatedUser = userService.updateUser(userId, req);

        assertEquals("New Name", updatedUser.getFull_name());
        assertEquals("New Picture", updatedUser.getProfile_picture());
    }

    @Test
    public void testFindUserById() throws UserException {
        Integer userId = 1;
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User foundUser = userService.findUserById(userId);

        assertEquals(userId, foundUser.getId());
    }

    @Test
    public void testFindUserByIdNotFound() {
        Integer userId = 1;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserException.class, () -> {
            userService.findUserById(userId);
        });
    }

    @Test
    public void testFindUserProfile() {
        String jwt = "jwtToken";
        String email = "email@example.com";
        User user = new User();
        user.setEmail(email);

        when(jwtTokenProvider.getEmailFromToken(jwt)).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        User foundUser = userService.findUserProfile(jwt);

        assertEquals(email, foundUser.getEmail());
    }

    @Test
    public void testFindUserProfileWithInvalidToken() {
        String jwt = "invalidToken";

        when(jwtTokenProvider.getEmailFromToken(jwt)).thenReturn(null);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(BadCredentialsException.class, () -> {
            userService.findUserProfile(jwt);
        });
    }


    @Test
    public void testSearchUser() {
        String query = "test";
        List<User> users = Arrays.asList(new User(), new User());

        when(userRepository.searchUsers(query)).thenReturn(users);

        List<User> foundUsers = userService.searchUser(query);

        assertEquals(2, foundUsers.size());
    }




}