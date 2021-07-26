package com.wipro.learning.service;

import java.util.HashSet;
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
import com.wipro.learning.model.UserContentResponseDto;
import com.wipro.learning.model.UserContentResponseDto.ContentDto;
import com.wipro.learning.model.SubscribeRequestDto;
import com.wipro.learning.model.SubscribeToContentRequestDto;
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

	public ResponseEntity<?> subscribeToPlans(SubscribeRequestDto subscribeRequest) {

		// First Verify Payment
		verifyPayment(subscribeRequest);

		String learnerId = subscribeRequest.getLearnerid();
		User user = userRepository.findById(subscribeRequest.getUserid())
				.orElseThrow(() -> new RuntimeException("User ID not found"));

		// New User - Subscribing first time
		if (!StringUtils.hasText(subscribeRequest.getLearnerid())) {
			learnerId = UUID.randomUUID().toString();
			user.setLearnerId(Objects.nonNull(user.getLearnerId()) ? user.getLearnerId() : learnerId);

		}
		// Existing User - Enhancing Subscription
		else {
			if (user.getPlanId() > subscribeRequest.getPlan().getPlanId()) {
				throw new IllegalArgumentException("Cannot downgrade Subscription");
			}
		}

		user.setPlanId(subscribeRequest.getPlan().getPlanId());
		userRepository.save(user);
		
		Learner learnerEntity = new Learner();
		learnerEntity.setId(learnerId);
		learnerRepository.save(learnerEntity);

		return ResponseEntity
				.ok("User/Learner subscribed successfully for " + subscribeRequest.getPlan().getName() + " Plan");

	}

	public ResponseEntity<?> subscribeToContents(SubscribeToContentRequestDto subscribeToContent) {

		User user = userRepository.findById(subscribeToContent.getUserid())
				.orElseThrow(() -> new RuntimeException("User ID not found"));

		Learner learnerEntity = learnerRepository.findById(user.getLearnerId())
				.orElseThrow(() -> new RuntimeException("Learner ID not found"));

		Set<Content> contents = subscribeToContent.getContentid().stream().map(contentId -> {
			return contentRepository.findById(contentId).orElse(new Content());
		}).collect(Collectors.toSet());

		learnerEntity.getContents().addAll(contents);

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

	/**
	 * Method to create Contents
	 * 
	 * @param contentRequest {@link ContentRequestDto}
	 * @return - return message on successful creation
	 */
	public ResponseEntity<?> createContent(ContentRequestDto contentRequest) {

		Content contentEntity = new Content();
		contentEntity.setCreatorId(contentRequest.getUserid());
		contentEntity.setData(contentRequest.getData().getBytes());
		contentEntity.setTitle(contentRequest.getTitle());

		contentRepository.save(contentEntity);

		return ResponseEntity.ok(contentRequest.getTitle() + " content created Successfully");
	}

	/**
	 * Method to update existing content
	 * 
	 * @param contentId      {@link Integer}
	 * @param contentRequest {@link ContentRequestDto}
	 * @return - returns proper message successful update
	 */
	public ResponseEntity<?> updateContent(Integer contentId, ContentRequestDto contentRequest) {

		Content contentEntity = contentRepository.findById(contentId)
				.orElseThrow(() -> new RuntimeException("Content not found"));

		contentEntity.setData(contentRequest.getData().getBytes());

		contentRepository.save(contentEntity);

		return ResponseEntity.ok(contentRequest.getTitle() + " content updated Successfully");
	}

	public ResponseEntity<?> retrieveSubscribedContents(Integer userId) {

		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User ID not found"));

		if (Objects.isNull(user.getLearnerId())) {
			return ResponseEntity.ok("User with ID: " + user.getId() + " NOT subscribed to any course/s");
		}

		Learner learner = learnerRepository.findById(user.getLearnerId()).orElse(new Learner());

		UserContentResponseDto contentResponseDto = new UserContentResponseDto();
		contentResponseDto.setUserId(userId);
		contentResponseDto.setLearnerId(learner.getId());

		Set<ContentDto> contents = new HashSet<>();

		learner.getContents().forEach(content -> {
			contents.add(ContentDto.builder().creatorId(content.getCreatorId()).data(new String(content.getData()))
					.title(content.getTitle()).id(content.getId()).build());
		});

		contentResponseDto.setContents(contents);

		return ResponseEntity.ok(contentResponseDto);
	}
}
