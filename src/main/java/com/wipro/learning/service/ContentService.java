package com.wipro.learning.service;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.wipro.learning.domain.Content;
import com.wipro.learning.domain.Learner;
import com.wipro.learning.model.ContentRequestDto;
import com.wipro.learning.model.SubscribeRequestDto;
import com.wipro.learning.repository.ContentRepository;
import com.wipro.learning.repository.LearnerRepository;
import com.wipro.payment.model.PaymentDto;
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

	@Autowired
	private RestTemplate restTemplate;

	public ResponseEntity<?> subscribeToContents(SubscribeRequestDto subscribeRequest) {

		// First Verify Payment
		verifyPayment(subscribeRequest);

		String learnerId = subscribeRequest.getLearnerid();

		if (!StringUtils.hasText(subscribeRequest.getLearnerid())) {
			learnerId = UUID.randomUUID().toString();
			// Update user_id
			User user = userRepository.findById(subscribeRequest.getUserid())
					.orElseThrow(() -> new RuntimeException("User ID not found"));
			user.setLearnerId(Objects.nonNull(user.getLearnerId()) ? user.getLearnerId() : learnerId);
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

	private void verifyPayment(SubscribeRequestDto subscribeRequest) {

		PaymentDto paymentServiceDto = PaymentDto.builder().username(subscribeRequest.getPayment().getUsername())
				.userid(subscribeRequest.getUserid()).amount(subscribeRequest.getPayment().getAmount())
				.cardno(subscribeRequest.getPayment().getCardno()).cvv(subscribeRequest.getPayment().getCvv())
				.phone(subscribeRequest.getPayment().getPhone()).email(subscribeRequest.getPayment().getEmail())
				.expirydate(subscribeRequest.getPayment().getExpirydate()).build();

		ResponseEntity<String> paymentResponse = restTemplate.postForEntity("http://payment:8200/api/payment/create",
				paymentServiceDto, String.class);

		if (paymentResponse.getStatusCode().isError()) {
			throw new RuntimeException("Error occurred while performing Payments");
		}
	}

	public ResponseEntity<?> createContent(ContentRequestDto contentRequest) {

		Content contentEntity = new Content();
		contentEntity.setCreatorId(contentRequest.getUserid());
		contentEntity.setData(contentRequest.getData().getBytes());
		contentEntity.setTitle(contentRequest.getTitle());

		contentRepository.save(contentEntity);

		return ResponseEntity.ok(contentRequest.getTitle() + " content created Successfully");
	}

	public ResponseEntity<?> retrieveSubscribedContents(Integer userId) {

		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User ID not found"));

		Learner learner = learnerRepository.findById(user.getLearnerId()).orElse(new Learner());

		return ResponseEntity.ok(learner);
	}
}
