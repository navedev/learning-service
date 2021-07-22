package com.wipro.learning.model;

import java.util.Set;

import com.wipro.reglogin.domain.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UsersDto {

	private Integer id;

	private String username;

	private String email;

	private Set<Role> roles;

	private String learnerId;
}
