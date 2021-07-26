package com.wipro.learning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.learning.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
@Slf4j
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping(path = "/{user_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> retrieveUserInfo(@PathVariable(value = "user_id") Integer userId) {
		log.info("Request received to retrieve User info for User ID: {}", userId);
		return userService.retrieveUserInfo(userId);
	}

}
