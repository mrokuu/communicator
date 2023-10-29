package com.example.comminicator.service;

import com.example.comminicator.exception.UserException;
import com.example.comminicator.model.User;
import com.example.comminicator.common.request.UpdateUserRequest;
import com.example.comminicator.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.comminicator.configuration.JwtTokenProvider;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    public User updateUser(Integer userId, UpdateUserRequest req) throws UserException {

        logger.info("Updating user with ID {}", userId);
        User user = findUserById(userId);

        if(req.getFull_name() != null) {
            user.setFullName(req.getFull_name());
        }
        if(req.getProfile_picture() != null) {
            user.setProfilePicture(req.getProfile_picture());
        }

        return userRepo.save(user);
    }


    public User findUserById(Integer userId) throws UserException {
        return userRepo.findById(userId)
                .orElseThrow(() -> new UserException("User not exist with ID " + userId));
    }


    public User findUserProfile(String jwt) {
        String email = jwtTokenProvider.getEmailFromToken(jwt);
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Invalid token for email: " + email));
    }


    public List<User> searchUser(String query) {
        logger.info("Searching for users with query {}", query);
        return userRepo.searchUsers(query);
    }
}
