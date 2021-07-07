package com.wipro.learning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.learning.domain.Plan;

@Repository
public interface AdminRepository extends JpaRepository<Plan, String>{

}
