package com.wipro.learning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.learning.domain.Learner;

@Repository
public interface LearnerRepository extends JpaRepository<Learner, String> {

}
