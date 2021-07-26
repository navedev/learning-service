package com.wipro.learning.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.wipro.learning.model.UsersDto;
import com.wipro.reglogin.domain.User;
import com.wipro.reglogin.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public ResponseEntity<?> retrieveUserInfo(Integer userId) {

		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User ID not found"));

		return ResponseEntity.ok(UsersDto.builder().id(user.getId()).email(user.getEmail()).username(user.getUsername())
				.roles(user.getRoles()).learnerId(user.getLearnerId()).build());

	}

}
