package com.wipro.learning.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.learning.model.ContentRequestDto;
import com.wipro.learning.model.SubscribeRequestDto;
import com.wipro.learning.service.ContentService;

@RestController
@RequestMapping("/api/content")
public class ContentController {

	@Autowired
	private ContentService contentService;

	@PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createContent(@Valid @RequestBody ContentRequestDto contentRequest) {
		return contentService.createContent(contentRequest);
	}

	@PostMapping(path = "/subscribe", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> subscribeToContents(@Valid @RequestBody SubscribeRequestDto subscribeRequest) {
		return contentService.subscribeToContents(subscribeRequest);
	}
	
	@GetMapping(path = "/retrieve/{user_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> retrieveSubscribedContents(@PathVariable(value = "user_id") Integer userId) {
		return contentService.retrieveSubscribedContents(userId);
	}
}
