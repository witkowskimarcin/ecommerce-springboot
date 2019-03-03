package com.example.repository;

import com.example.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository("orderRepository")
public interface OrderRepository extends JpaRepository<Order, Long> {
	
	Order findById(long id);

}
