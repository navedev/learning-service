package com.wipro.learning.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.learning.domain.Content;

@Repository
public interface ContentRepository extends JpaRepository<Content, Integer>{
	
	Optional<Content> findById(Integer id);

}
