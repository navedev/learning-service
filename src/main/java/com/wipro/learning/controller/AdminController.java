package com.wipro.learning.controller;

import java.util.List;

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

import com.wipro.learning.model.PlanDto;
import com.wipro.learning.service.AdminService;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller class for Admin operations
 * 
 * @author Naveen Devarajaiah
 * @since 07-Jul-2021
 *
 */
@RestController
@RequestMapping("/api/admin")
@CrossOrigin
@Slf4j
public class AdminController {

	@Autowired
	private AdminService adminService;

	/**
	 * Method to create Packages or Plans (Only by Admins)
	 * 
	 * @param planDtoList - List of Plans {@link List}
	 * @return - returns Plans created message else exception
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping(path = "/plan", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createPlans(@Valid @RequestBody List<PlanDto> planDtoList) {
		log.info("Request to create Plans received");
		return adminService.createPlans(planDtoList);
	}

	@GetMapping(path = "/getPlans", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getPlans() throws NotFoundException {
		log.info("Request to fetch Plans received");
		return adminService.retrievePlans();
	}

	/**
	 * Method to retrieve User information by Admins
	 * 
	 * @return - returns List of Users with some details for reporting purpose
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping(path = "/app-users", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> retrieveUsers() {
		log.info("Admin trying to retrieve Users");
		return adminService.retrieveUsers();
	}

	/**
	 * Method to enhance User to Content Creator
	 * 
	 * @param userId - User ID {@link Integer}
	 * @return - returns success on adding Content Creator Role
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping(path = "/content-creator/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> validateContentCreator(@PathVariable(name = "id") Integer userId) {
		log.info("Request to validate Content Creator received");
		return adminService.validateContentCreator(userId);
	}
}
