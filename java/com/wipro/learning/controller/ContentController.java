package com.wipro.learning.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.learning.model.ContentRequest;
import com.wipro.learning.model.SubscribeRequest;
import com.wipro.learning.service.ContentService;

@RestController
@RequestMapping("/api/content")
public class ContentController {

	@Autowired
	private ContentService contentService;

	@PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createContent(@Valid @RequestBody ContentRequest contentRequest) {
		return contentService.createContent(contentRequest);
	}

	@PostMapping(path = "/subscribe", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> subscribeToContents(@Valid @RequestBody SubscribeRequest subscribeRequest) {
		return contentService.subscribeToContents(subscribeRequest);
	}

}
