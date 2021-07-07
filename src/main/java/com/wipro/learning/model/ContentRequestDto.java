package com.wipro.learning.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContentRequestDto {
	
	private String title;
	
	private String data;
	
	private Integer userid;

}
