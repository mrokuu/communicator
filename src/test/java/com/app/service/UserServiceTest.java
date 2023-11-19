package com.app.service;

import com.app.config.JwtTokenProvider;
import com.app.exception.UserException;
import com.app.modal.User;
import com.app.repository.UserRepository;
import com.app.common.request.UpdateUserRequest;
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
        Long userId = 1L;
        UpdateUserRequest req = new UpdateUserRequest();
        req.setFull_name("New Name");
        req.setProfile_picture("New Picture");

        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        User updatedUser = userService.updateUser(userId, req);

        assertEquals("New Name", updatedUser.getFullName());
        assertEquals("New Picture", updatedUser.getProfile_picture());
    }

    @Test
    public void testFindUserById() throws UserException {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User foundUser = userService.findUserById(userId);

        assertEquals(userId, foundUser.getId());
    }

    @Test
    public void testFindUserByIdNotFound() {
        Long userId = 1L;
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

    @Test
    public void testFindUserByIdWithValidId() throws UserException {
        Long userId = 1L;
        User expectedUser = new User(); // assume this is a populated user object
        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));

        User result = userService.findUserById(userId);

        assertEquals(expectedUser, result);
    }

    @Test
    public void testUpdateUserWithValidData() throws UserException {
        Long userId = 1L;
        UpdateUserRequest req = new UpdateUserRequest();
        req.setFull_name("John Doe");
        req.setProfile_picture("profile_pic.jpg");

        User existingUser = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        User updatedUser = userService.updateUser(userId, req);

        assertEquals("John Doe", updatedUser.getFullName());
        assertEquals("profile_pic.jpg", updatedUser.getProfile_picture());
    }

    @Test
    public void testFindUserProfileWithValidToken() {
        String jwt = "valid.jwt.token";
        String email = "test@example.com";
        User expectedUser = new User();
        expectedUser.setEmail(email);

        when(jwtTokenProvider.getEmailFromToken(jwt)).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(expectedUser));

        User result = userService.findUserProfile(jwt);

        assertEquals(expectedUser, result);
    }


    



}