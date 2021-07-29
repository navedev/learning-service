package com.wipro.learning.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.wipro.learning.domain.Plan;
import com.wipro.learning.model.PlanDto;
import com.wipro.learning.model.UsersDto;
import com.wipro.learning.repository.PlanRepository;
import com.wipro.reglogin.domain.User;
import com.wipro.reglogin.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PlanRepository planRepository;

	public ResponseEntity<?> retrieveUserInfo(Integer userId) {

		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User ID not found"));
		UsersDto.UsersDtoBuilder userDtoBuilder = UsersDto.builder().id(user.getId()).email(user.getEmail())
				.username(user.getUsername()).roles(user.getRoles()).learnerId(user.getLearnerId());
		if (Objects.nonNull(user.getPlanId())) {
			// Retrieve User Subscribed Plan info
			Plan subscribedPlan = planRepository.findById(user.getPlanId())
					.orElseThrow(() -> new RuntimeException("Plan not found"));
			userDtoBuilder.planDto(PlanDto.builder().name(subscribedPlan.getName()).userid(user.getId())
					.numberofcourses(subscribedPlan.getNumberofcourses()).planId(subscribedPlan.getId())
					.price(subscribedPlan.getPrice()).build());
		}

		return ResponseEntity.ok(userDtoBuilder.build());

	}

}
