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
        String full_name=user.getFull_name();

        Optional<User> isEmailExist=userRepository.findByEmail(email);

        if (isEmailExist.isPresent()) {
            System.out.println("--------- exist "+isEmailExist.get().getEmail());

            throw new UserException("Email Is Already Used With Another Account");
        }

        User createdUser= new User();
        createdUser.setEmail(email);
        createdUser.setFull_name(full_name);
        createdUser.setPassword(passwordEncoder.encode(password));



        userRepository.save(createdUser);


        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateJwtToken(authentication);

        AuthResponse authResponse= new AuthResponse();

        authResponse.setStatus(true);
        authResponse.setJwt(token);

        return authResponse;
    }
}
