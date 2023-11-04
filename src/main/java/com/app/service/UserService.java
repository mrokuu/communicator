package com.app.service;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.config.JwtTokenProvider;
import com.app.exception.UserException;
import com.app.modal.User;
import com.app.repository.UserRepository;
import com.app.request.UpdateUserRequest;

@Service
@AllArgsConstructor
public class UserService  {
	

	private PasswordEncoder passwordEncoder;
	

	private UserRepository userRepository;
	

	private JwtTokenProvider jwtTokenProvider;


	public User updateUser(Integer userId, UpdateUserRequest req) throws UserException {
		
		User user=findUserById(userId);
		

		if(req.getFull_name()!=null) {
			user.setFull_name(req.getFull_name());
		}
		if(req.getProfile_picture()!=null) {
			user.setProfile_picture(req.getProfile_picture());
		}
		
		return userRepository.save(user);
	}


	public User findUserById(Integer userId) throws UserException {
		
		Optional<User> opt=userRepository.findById(userId);
		
		if(opt.isPresent()) {
			User user=opt.get();
			
			return user;
		}
		throw new UserException("user not exist with id "+userId);
	}


	public User findUserProfile(String jwt) {
		String email = jwtTokenProvider.getEmailFromToken(jwt);
		
		Optional<User> opt=userRepository.findByEmail(email);
		
		if(opt.isPresent()) {
			return opt.get();
		}
		
		throw new BadCredentialsException("recive invalid token");
	}


	public List<User> searchUser(String query) {
		return userRepository.searchUsers(query);
		
	}
	
	

}
