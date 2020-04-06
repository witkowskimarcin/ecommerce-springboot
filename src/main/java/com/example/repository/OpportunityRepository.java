package com.example.repository;

import com.example.entity.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OpportunityRepository extends JpaRepository<Opportunity, Long> {


}
