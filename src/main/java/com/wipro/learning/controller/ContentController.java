package com.wipro.learning.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.learning.model.ContentRequestDto;
import com.wipro.learning.model.SubscribeRequestDto;
import com.wipro.learning.service.ContentService;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller class to handle Requests related to Content creation, subscription
 * and others
 * 
 * @author Naveen Devarajaiah
 *
 */
@RestController
@RequestMapping("/api/content")
@CrossOrigin
@Slf4j
public class ContentController {

	@Autowired
	private ContentService contentService;

	/**
	 * Method to create content
	 * 
	 * @param contentRequest - Content creation request {@link ContentRequestDto}
	 * @return - returns success on creation
	 */
	@PreAuthorize("hasRole('ROLE_CREATOR')")
	@PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createContent(@Valid @RequestBody ContentRequestDto contentRequest) {
		log.info("Request received to create Content with Title: ", contentRequest.getTitle());
		return contentService.createContent(contentRequest);
	}

	/**
	 * Method to update existing content
	 * 
	 * @param id             - Content ID {@link Integer}
	 * @param contentRequest - Content Request {@link ContentRequestDto}
	 * @return - returns message on success
	 */
	@PreAuthorize("hasRole('ROLE_CREATOR')")
	@PutMapping(path = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateContent(@PathVariable(name = "id") Integer contentId,
			@Valid @RequestBody ContentRequestDto contentRequest) {
		log.info("Request received to update Content with Title: {}", contentRequest.getTitle());
		return contentService.updateContent(contentId, contentRequest);
	}

	/**
	 * Method to Subscribe to different contents per user
	 * 
	 * @param subscribeRequest - Subscription request {@link SubscribeRequestDto}
	 * @return - return proper message on success else exception
	 */
	@PostMapping(path = "/subscribe", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> subscribeToContents(@Valid @RequestBody SubscribeRequestDto subscribeRequest) {
		log.info("User: {} is subscribing to contents", subscribeRequest.getUserid());
		return contentService.subscribeToContents(subscribeRequest);
	}

	/**
	 * Method to retrieve subscribed contents
	 * 
	 * @param userId - User ID {@link Integer}
	 * @return - returns content subscribed by a User
	 */
	@GetMapping(path = "/retrieve/{user_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> retrieveSubscribedContents(@PathVariable(value = "user_id") Integer userId) {
		log.info("Request received to retrieve subscribed contents for User: {}", userId);
		return contentService.retrieveSubscribedContents(userId);
	}
}
