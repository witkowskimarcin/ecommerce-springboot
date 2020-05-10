package com.example.service;

import com.example.model.OrderDetailModel;
import com.example.model.OrderForm;
import com.example.model.OrderModel;

import java.util.List;

public interface OrderService
{
    List<OrderModel> getAll();
    List<OrderModel> getMyOrders();
    OrderModel getOrder(Long id);
    void makeOrder(OrderForm order);
    List<OrderDetailModel> getDetailsByOrderId(Long id);
    void removeOrder(Long id);
}
