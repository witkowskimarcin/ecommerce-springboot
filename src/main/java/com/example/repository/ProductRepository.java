package com.example.repository;

import java.util.ArrayList;

import com.example.entity.Product;
import com.example.entity.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("productRepository")
public interface ProductRepository extends JpaRepository<Product, Long> {

	ArrayList<Product> findAllBySubcategory(Subcategory subcategory);
}
