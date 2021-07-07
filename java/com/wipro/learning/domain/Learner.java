package com.wipro.learning.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "learner")
@Data
@NoArgsConstructor
public class Learner {
	
	@Id
	private String id;
	
	@ManyToMany
	@JoinTable(
	  name = "learner_content", 
	  joinColumns = @JoinColumn(name = "learner_id"), 
	  inverseJoinColumns = @JoinColumn(name = "content_id"))
	private Set<Content> contents;

}
