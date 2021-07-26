package com.wipro.learning.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ContentsDto {
	
	private Integer id;

	private String title;

	private String data;

	private Integer creatorId;

}
