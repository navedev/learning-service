package com.wipro.learning.model;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContentResponseDto {

	private Integer userId;

	private String learnerId;

	Set<ContentDto> contents;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class ContentDto {

		private Integer id;

		private String title;

		private String data;

		private Integer creatorId;

	}

}
