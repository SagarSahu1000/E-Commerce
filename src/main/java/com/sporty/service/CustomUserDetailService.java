package com.sporty.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sporty.model.CustomUserDetail;
import com.sporty.model.User;
import com.sporty.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService{
    
	@Autowired
	UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findUserByEmail(email);
		user.orElseThrow(() -> new UsernameNotFoundException("couldnt get user"));
		return user.map(CustomUserDetail:: new).get();
		
	}

}
