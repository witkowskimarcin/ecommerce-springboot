package com.example.service;

import com.example.entity.Order;
import com.example.entity.OrderDetail;
import com.example.entity.User;
import com.example.exception.ResourceNotFoundException;
import com.example.model.OrderDetailModel;
import com.example.model.OrderForm;
import com.example.model.OrderModel;
import com.example.repository.OrderDetailRepository;
import com.example.repository.OrderRepository;
import com.example.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService
{
    private OrderRepository orderRepository;
    private OrderDetailRepository orderDetailsRepository;
    private ProductRepository productRepository;
    private UserService userService;
    private Mappers mappers;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public OrderServiceImpl(OrderRepository orderRepository, OrderDetailRepository orderDetailsRepository, ProductRepository productRepository, UserService userService, Mappers mappers) {
        this.orderRepository = orderRepository;
        this.orderDetailsRepository = orderDetailsRepository;
        this.productRepository = productRepository;
        this.userService = userService;
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

    @Override
    public List<OrderModel> getMyOrders() {
        return orderRepository
                .findByCustomer(userService.getCurrentUser())
                .stream()
                .map(mappers::mapOrderEntityToModel)
                .collect(Collectors.toList());
    }

    @Override
    public OrderModel getOrder(Long id) {
        return mappers.mapOrderEntityToModel(orderRepository
                        .findById(id)
                        .orElseThrow(()->new ResourceNotFoundException("Order "+id+ " does not exist")));
    }

    @Override
    public void makeOrder(OrderForm order) {

        User u = userService.getCurrentUser();

        Order o = new Order();
        order.getProductIdsAndQuantities().forEach( (prodId, quantity) -> {
            OrderDetail od = new OrderDetail();
            od.setProduct(productRepository.findById(prodId).orElseThrow(()->new ResourceNotFoundException("Product "+prodId+ " does not exist")));
            od.setQuantity(Math.toIntExact(quantity));
            o.getOrderDetails().add(od);
        });

        o.setFirstname(u.getFirstname());
        o.setLastname(u.getLastname());
        o.setFirstname(u.getFirstname());
        o.setFirstname(u.getFirstname());

        logger.info("Order has been made successfully");
    }

    @Override
    public List<OrderDetailModel> getDetailsByOrderId(Long id)
    {
        return orderRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Order id: "+id+" does not exist"))
                .getOrderDetails()
                .stream()
                .map(mappers::mapOrderDetailEntityToModel)
                .collect(Collectors.toList());
    }

    @Override
    public void removeOrder(Long id) {

        Order o = orderRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Order id: "+id+" does not exist"));
        o.getOrderDetails().forEach(od -> {
            orderDetailsRepository.deleteById(od.getId());
        });
        orderRepository.deleteById(id);

        logger.info("Order has been removed successfully");
    }
}
