package com.example.service;

import com.example.model.OpportunityModel;
import com.example.repository.OpportunityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OpportunityServiceImpl implements OpportunityService
{
    private OpportunityRepository opportunityRepository;
    private Mappers mappers;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public OpportunityServiceImpl(OpportunityRepository opportunityRepository, Mappers mappers)
    {
        this.opportunityRepository = opportunityRepository;
        this.mappers = mappers;
    }

    @Override
    public OpportunityModel getOpportunity()
    {
        return mappers.mapOpportunityEntityToModel(opportunityRepository.findAll().get(0));
    }

    @Override
    public void setOpportunity(OpportunityModel opportunity)
    {
        opportunityRepository.deleteAll();
        opportunityRepository.save(mappers.mapOpportunityModelToEntity(opportunity));
        logger.info("Set opportunity");
    }

    @Override
    public void unsetOpportunity()
    {
        opportunityRepository.deleteAll();
        logger.info("Unset opportunity");
    }
}
