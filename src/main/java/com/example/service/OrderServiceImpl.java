package com.example.service;

import com.example.model.OrderModel;
import com.example.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService
{
    private OrderRepository orderRepository;
    private Mappers mappers;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public OrderServiceImpl(OrderRepository orderRepository, Mappers mappers)
    {
        this.orderRepository = orderRepository;
        this.mappers = mappers;
    }

    @Override
    public List<OrderModel> getAll()
    {
        return orderRepository
                .findAll()
                .stream()
                .map(mappers::mapOrderEntityToModel)
                .collect(Collectors.toList());
    }
}
