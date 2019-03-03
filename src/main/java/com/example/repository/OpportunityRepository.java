package com.example.repository;

import com.example.model.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OpportunityRepository extends JpaRepository<Opportunity, Long> {


}
