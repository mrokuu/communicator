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

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final UserRepository userRepo;

    @Autowired
    private final JwtTokenProvider jwtTokenProvider;


    public User updateUser(Integer userId, UpdateUserRequest req) throws UserException {

        User user=findUserById(userId);
        if(req.getFullName()!=null) {
            user.setFullName(req.getFullName());
        }
        if(req.getProfilePicture()!=null) {
            user.setProfilePicture(req.getProfilePicture());
        }

        return userRepo.save(user);
    }


    public User findUserById(Integer userId) throws UserException {

        Optional<User> opt=userRepo.findById(userId);

        if(opt.isPresent()) {
            User user=opt.get();

            return user;
        }
        throw new UserException("user not exist with id "+userId);
    }


    public User findUserProfile(String jwt) {
        String email = jwtTokenProvider.getEmailFromToken(jwt);

        Optional<User> opt=userRepo.findByEmail(email);

        if(opt.isPresent()) {
            return opt.get();
        }

        throw new BadCredentialsException("recive invalid token");
    }


    public List<User> searchUser(String query) {
        return userRepo.searchUsers(query);

    }
}
