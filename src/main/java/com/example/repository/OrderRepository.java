package com.example.repository;

import com.example.entity.Order;
import com.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;


@Repository("orderRepository")
public interface OrderRepository extends JpaRepository<Order, Long> {
	
	Order findById(long id);
    List<Order> findByCustomer(User customer);
}
