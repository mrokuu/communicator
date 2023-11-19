package com.app.service;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.config.JwtTokenProvider;
import com.app.exception.UserException;
import com.app.modal.User;
import com.app.repository.UserRepository;
import com.app.common.request.UpdateUserRequest;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserService  {
	

	private PasswordEncoder passwordEncoder;
	

	private UserRepository userRepository;
	

	private JwtTokenProvider jwtTokenProvider;


	public User updateUser(Long userId, UpdateUserRequest req) throws UserException {
		User user = findUserById(userId);

		Optional.ofNullable(req.getFull_name()).ifPresent(user::setFullName);
		Optional.ofNullable(req.getProfile_picture()).ifPresent(user::setProfile_picture);

		return userRepository.save(user);
	}


	public User findUserById(Long userId) throws UserException {
		return userRepository.findById(userId)
				.orElseThrow(() -> new UserException("User not exist with id " + userId));
	}


	public User findUserProfile(String jwt) {
		String jwtToken = jwtTokenProvider.getEmailFromToken(jwt);
		return userRepository.findByEmail(jwtToken)
				.orElseThrow(() -> new BadCredentialsException("Received invalid token"));

	}


	public List<User> searchUser(String query) {
		return userRepository.searchUsers(query);
		
	}




}
