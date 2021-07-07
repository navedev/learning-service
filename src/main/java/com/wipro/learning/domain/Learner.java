package com.wipro.learning.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "learner")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Learner {
	
	@Id
	private String id;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(
	  name = "learner_content", 
	  joinColumns = @JoinColumn(name = "learner_id"), 
	  inverseJoinColumns = @JoinColumn(name = "content_id"))
	@JsonIgnoreProperties("learners")
	private Set<Content> contents = new HashSet<>();

}
