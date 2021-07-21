package com.wipro.learning.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.wipro.learning.domain.Plan;
import com.wipro.learning.model.PlanDto;
import com.wipro.learning.repository.AdminRepository;
import com.wipro.reglogin.domain.Role;
import com.wipro.reglogin.domain.User;
import com.wipro.reglogin.model.RoleEnum;
import com.wipro.reglogin.repository.RoleRepository;
import com.wipro.reglogin.repository.UserRepository;

@Service
public class AdminService {

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	public ResponseEntity<?> createPlans(List<PlanDto> planDtoList) {
		if (!CollectionUtils.isEmpty(planDtoList)) {
			planDtoList.stream().forEach(planDto -> {
				Plan plan = Plan.builder().name(planDto.getName()).price(planDto.getPrice())
						.numberofcourses(planDto.getNumberofcourses()).createdby(planDto.getUserid()).build();

				adminRepository.save(plan);
			});
		}

		return ResponseEntity.ok("Plan/s created successfully");
	}

	public ResponseEntity<?> retrieveUsers() {
		return null;
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

}
