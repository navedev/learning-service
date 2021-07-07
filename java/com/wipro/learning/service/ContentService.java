package com.wipro.learning.service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.wipro.learning.domain.Content;
import com.wipro.learning.domain.Learner;
import com.wipro.learning.model.ContentRequest;
import com.wipro.learning.model.SubscribeRequest;
import com.wipro.learning.repository.ContentRepository;
import com.wipro.learning.repository.LearnerRepository;
import com.wipro.reglogin.domain.User;
import com.wipro.reglogin.repository.UserRepository;

@Service
public class ContentService {

	@Autowired
	private ContentRepository contentRepository;

	@Autowired
	private LearnerRepository learnerRepository;

	@Autowired
	private UserRepository userRepository;

	public ResponseEntity<?> subscribeToContents(SubscribeRequest subscribeRequest) {

		String learnerId = subscribeRequest.getLearnerid();

		if (null == subscribeRequest.getLearnerid()) {
			learnerId = UUID.randomUUID().toString();
			// Update user_id
			User user = userRepository.findById(subscribeRequest.getUserid())
					.orElseThrow(() -> new RuntimeException("User ID not found"));
			user.setLearnerId(learnerId);
			userRepository.save(user);
		}

		Learner learnerEntity = new Learner();
		learnerEntity.setId(learnerId);

		Set<Content> contents = subscribeRequest.getContentid().stream().map(contentId -> {
			return contentRepository.findById(contentId).orElse(new Content());
		}).collect(Collectors.toSet());

		learnerEntity.setContents(contents);

		learnerRepository.save(learnerEntity);

		return ResponseEntity.ok("User/Learner subscribed successfully for courses/contents");
	}

	public ResponseEntity<?> createContent(ContentRequest contentRequest) {

		Content contentEntity = new Content();
		contentEntity.setCreatorId(contentRequest.getUserid());
		contentEntity.setData(contentRequest.getData().getBytes());
		contentEntity.setTitle(contentRequest.getTitle());

		contentRepository.save(contentEntity);

		return ResponseEntity.ok(contentRequest.getTitle() + " content created Successfully");
	}
}
