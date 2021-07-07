package com.wipro.learning.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "content")
@Getter
@Setter
@AllArgsConstructor
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

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "contents")
	@JsonIgnoreProperties("contents")
	private Set<Learner> learners = new HashSet<>();

}
