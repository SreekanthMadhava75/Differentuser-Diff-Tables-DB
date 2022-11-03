package com.example.demo.service;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	
	private final ModelMapper modelMapper;
	private final PasswordEncoder passwordEncoder;
	
	public void save(UserDTO userDTO) {
		userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		User user = mapToEntity(userDTO);
		user.setUserId(UUID.randomUUID().toString());
		userRepository.save(user);
		
	}

	private User mapToEntity(UserDTO userDTO) {
		// TODO Auto-generated method stub
		return modelMapper.map(userDTO, User.class);
	}
	
	
	
	//get the data from based on logged on user
	public User getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUserEmail = auth.getName();
        return userRepository.findByEmail(loggedInUserEmail).orElseThrow(() ->
                new UsernameNotFoundException("User not found for the email:"+loggedInUserEmail));
    }

}
