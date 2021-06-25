package com.example.repository;

import com.example.entity.Order;
import com.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Repository("orderRepository")
public interface OrderRepository extends JpaRepository<Order, Long> {

//    Order findById(long id);
    List<Order> findByCustomer(User customer);
}
