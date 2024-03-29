package com.wipro.learning.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubscribeRequestDto {

	private Integer userid;

	private String learnerid;

	private PlanDto plan;

	private PaymentDto payment;

}
