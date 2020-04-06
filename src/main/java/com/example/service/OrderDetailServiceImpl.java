package com.example.service;

import com.example.model.OrderDetailModel;
import com.example.repository.OrderDetailRepository;
import com.example.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDetailServiceImpl implements OrderDetailService
{
    private OrderRepository orderRepository;
    private OrderDetailRepository orderDetailRepository;
    private Mappers mappers;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public OrderDetailServiceImpl(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository, Mappers mappers)
    {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.mappers = mappers;
    }

    @Override
    public List<OrderDetailModel> getDetailsByOrderId(Long id)
    {
        return orderRepository
                .findById(id)
                .orElseThrow(()-> new RuntimeException("OrderDetail id: "+id+" does not exist"))
                .getOrderDetails()
                .stream()
                .map(mappers::mapOrderDetailEntityToModel)
                .collect(Collectors.toList());
    }
}
