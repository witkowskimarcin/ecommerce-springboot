package com.example.service;

import com.example.model.OrderDetailModel;

import java.util.List;

public interface OrderDetailService
{
    List<OrderDetailModel> getDetailsByOrderId(Long id);
}
