package com.example.comminicator.service;

import com.example.comminicator.configuration.JwtTokenProvider;
import com.example.comminicator.exception.UserException;
import com.example.comminicator.model.User;
import com.example.comminicator.repository.UserRepository;
import com.example.comminicator.request.Login;
import com.example.comminicator.response.AuthResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.ArrayList;

@Service
@AllArgsConstructor
public class AuthService {
    private final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetails;



    public ResponseEntity<AuthResponse> createUserHandler(User user) throws UserException {

        String email = user.getEmail();
        String password = user.getPassword();
        String fullName = user.getFullName();

        validateUserDoesNotExist(email);

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setFullName(fullName);
        newUser.setPassword(passwordEncoder.encode(password));
        userRepository.save(newUser);

        Authentication authentication = authenticateUser(email, password);
        String token = jwtTokenProvider.generateJwtToken(authentication);

        AuthResponse authResponse= new AuthResponse();

        authResponse.setStatus(true);
        authResponse.setJwt(token);

        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);
    }



    public AuthResponse signin(Login login) {
        String username = login.getEmail();
        String password = login.getPassword();

        // Validate inputs
        if (username == null || password == null) {
            throw new IllegalArgumentException("Username and password must not be null");
        }

        try {
            Authentication authentication = authenticate(username, password);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtTokenProvider.generateJwtToken(authentication);
            AuthResponse authResponse = new AuthResponse();
            authResponse.setStatus(true);
            authResponse.setJwt(token);

            return authResponse;
        } catch (Exception e) {
            logger.error("Authentication failed for user: {}", username, e);
            throw e;
        }
    }





    private void validateUserDoesNotExist(String email) throws UserException {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserException("Email is already used with another account");
        }
    }

    private Authentication authenticateUser(String email, String password) {
        return new UsernamePasswordAuthenticationToken(email, password, new ArrayList<>());
    }

    public Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserDetails.loadUserByUsername(username);

        if (userDetails == null) {
            logger.error("User not found: {}", username);
            throw new UsernameNotFoundException("User not found");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            logger.error("Password mismatch for user: {}", username);
            throw new BadCredentialsException("Invalid credentials");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}
