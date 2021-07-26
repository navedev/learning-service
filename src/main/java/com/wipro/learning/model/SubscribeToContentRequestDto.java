package com.wipro.learning.model;

import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubscribeToContentRequestDto {

	private Integer userid;

	private String learnerid;

	private Set<Integer> contentid;

}
