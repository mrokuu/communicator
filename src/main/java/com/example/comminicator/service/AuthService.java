package com.example.comminicator.service;

import com.example.comminicator.configuration.JwtTokenProvider;
import com.example.comminicator.exception.UserException;
import com.example.comminicator.model.User;
import com.example.comminicator.repository.UserRepository;
import com.example.comminicator.response.AuthResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.ArrayList;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;


    public AuthResponse createUserHandler(User user) throws UserException {

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

        return new AuthResponse(token, true);
    }



    private void validateUserDoesNotExist(String email) throws UserException {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserException("Email is already used with another account");
        }
    }

    private Authentication authenticateUser(String email, String password) {
        return new UsernamePasswordAuthenticationToken(email, password, new ArrayList<>());
    }
}
