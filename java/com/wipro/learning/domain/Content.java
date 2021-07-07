package com.wipro.learning.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "content")
@Data
@NoArgsConstructor
public class Content {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank
	@Size(max = 150)
	private String title;

	@Lob
	private byte[] data;

	@NotNull
	@Column(name = "creator_id")
	private Integer creatorId;
	
	@ManyToMany(mappedBy = "contents")
	private Set<Learner> learners;

}
