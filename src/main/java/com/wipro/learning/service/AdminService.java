package com.wipro.learning.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.wipro.learning.domain.Plan;
import com.wipro.learning.model.PlanDto;
import com.wipro.learning.model.UsersDto;
import com.wipro.learning.repository.PlanRepository;
import com.wipro.reglogin.domain.Role;
import com.wipro.reglogin.domain.User;
import com.wipro.reglogin.model.RoleEnum;
import com.wipro.reglogin.repository.RoleRepository;
import com.wipro.reglogin.repository.UserRepository;

import javassist.NotFoundException;

@Service
public class AdminService {

	@Autowired
	private PlanRepository planRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	public ResponseEntity<?> createPlans(List<PlanDto> planDtoList) {
		if (!CollectionUtils.isEmpty(planDtoList)) {
			planDtoList.stream().forEach(planDto -> {
				Plan plan = Plan.builder().name(planDto.getName()).price(planDto.getPrice())
						.numberofcourses(planDto.getNumberofcourses()).createdby(planDto.getUserid()).build();

				planRepository.save(plan);
			});
		}

		return ResponseEntity.ok("Plan/s created successfully");
	}

	/**
	 * Method to retrieve all Users/Learners excluding Admins
	 * 
	 * @return - List of Users
	 */
	public ResponseEntity<?> retrieveUsers() {
		List<UsersDto> usersList = new ArrayList<>();
		userRepository.findAll().forEach(user -> {
			usersList.add(UsersDto.builder().id(user.getId()).email(user.getEmail()).username(user.getUsername())
					.roles(user.getRoles()).learnerId(user.getLearnerId()).build());
		});
		return ResponseEntity.ok(usersList);
	}

	/**
	 * Method to enhance User to Content Creator
	 * 
	 * @param userId - User ID {@link Integer}
	 * @return - returns success on adding Content Creator Role
	 */
	public ResponseEntity<?> validateContentCreator(Integer userId) {

		User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));

		Set<Role> userRoles = user.getRoles();

		// Add Content Creator Role
		Role creatorRole = roleRepository.findByRole(RoleEnum.ROLE_CREATOR)
				.orElseThrow(() -> new RuntimeException("Role not found"));
		creatorRole.setRole(RoleEnum.ROLE_CREATOR);

		userRoles.add(creatorRole);

		userRepository.save(user);

		return ResponseEntity.ok("Successfully enhanced User with ID: " + userId + " to a role of Content Creator");
	}

	/**
	 * Method to retrieve Plans
	 * 
	 * @return - List of Plans
	 * @throws NotFoundException
	 */
	public ResponseEntity<?> retrievePlans() throws NotFoundException {

		List<Plan> plans = planRepository.findAll();
		if (plans.isEmpty()) {
			throw new NotFoundException("Plans not found");
		}

		return ResponseEntity.ok(plans);
	}

}
