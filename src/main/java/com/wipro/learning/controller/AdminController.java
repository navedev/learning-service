package com.wipro.learning.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.learning.model.PlanDto;
import com.wipro.learning.service.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping(path = "/plan", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createPlans(@Valid @RequestBody List<PlanDto> planDtoList) {

		return adminService.createPlans(planDtoList);
	}

}
