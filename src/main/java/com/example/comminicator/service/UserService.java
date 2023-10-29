package com.example.comminicator.service;

import com.example.comminicator.exception.UserException;
import com.example.comminicator.model.User;
import com.example.comminicator.common.request.UpdateUserRequest;
import com.example.comminicator.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.comminicator.configuration.JwtTokenProvider;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepo;
    private JwtTokenProvider jwtTokenProvider;

    private final UserRepository userRepository;

    public User updateUser(Integer userId, UpdateUserRequest req) throws UserException {
        logger.info("Updating user with ID {}", userId);
        Optional<User> userWithID = userRepository.findById(userId);
        if(userWithID.isPresent()) {
            User user=userWithID.get();

            return user;
        }
        throw new UserException("user not exist with id "+userId);
    }

    public User getUser(String jwt) {
        String jwtToken = jwtTokenProvider.getEmailFromToken(jwt);
        return userRepository.findByEmail(jwtToken)
                .orElseThrow(() -> new BadCredentialsException("Invalid token received"));
    }

    public List<User> searchUser(String query) {
        logger.info("Searching for users with query {}", query);
        return userRepo.searchUsers(query);
    }
}
