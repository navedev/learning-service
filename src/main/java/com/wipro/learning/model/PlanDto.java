package com.wipro.learning.model;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlanDto {
	
	private Integer userid;
	
	private Long planId;
	
	private String name;

	private BigDecimal price;
	
	/**
	 * Can be Unlimited based on Plan
	 */
	private String numberofcourses;
}
