package com.wipro.learning.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.wipro.learning.domain.Plan;
import com.wipro.learning.model.PlanDto;
import com.wipro.learning.repository.AdminRepository;

@Service
public class AdminService {

	@Autowired
	private AdminRepository adminRepository;

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

}
