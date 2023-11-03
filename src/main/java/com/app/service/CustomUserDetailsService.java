package com.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.modal.User;
import com.app.repository.UserRepository;




@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;
	

	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		        Optional<User> user = userRepository.findByEmail(username);
		        
		        if (user.isEmpty()) {
		            throw new UsernameNotFoundException("User not found with username: " + username);
		        }
		        List<GrantedAuthority> authorities = new ArrayList<>();
		        
		        
		        return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(), authorities);
		    }
		

	}


