package com.usersystem.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.usersystem.controller.security.SecurityConfig;
import com.usersystem.model.User;
import com.usersystem.repository.UserRepository;



@Service
public class UserService implements UserDetailsService{
	
	private final UserRepository userRepository;
	
	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public User getById(long id) {
		return userRepository.findUserById(id);
	}
	
	public List<User> getAll() {
		return userRepository.findAll();
	}
	
	public User save(User user) {
		user.setPassword(SecurityConfig.passwordEncoder().encode(user.getPassword()));
		return userRepository.save(user);
	}
	
	public void update(long id, User user) {
		user.setId(id);
		userRepository.save(user);
	}
	
	public void delete(long id) {
		userRepository.deleteById(id);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.findUserByName(username);
		
		String role = user.getRole().name();
		
		UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(role)));
        return userDetails;
	}

}
