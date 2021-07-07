package com.wipro.learning.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.validation.constraints.Email;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentDto {
	
	private String username;
	
	@Email
	private String email;
	
	@Positive
	private BigInteger phone;
	
	private Long cardno;
	
	@Size(max = 4)
	private Integer cvv;
	
	private String expirydate;
	
	private BigDecimal amount;

}
