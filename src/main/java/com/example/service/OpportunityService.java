package com.example.service;

import com.example.model.OpportunityModel;

public interface OpportunityService
{
    OpportunityModel getOpportunity();

    void setOpportunity(OpportunityModel opportunity);

    void unsetOpportunity();
}
