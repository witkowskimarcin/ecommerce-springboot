package com.example.repository;

import java.util.ArrayList;

import com.example.model.Product;
import com.example.model.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("productRepository")
public interface ProductRepository extends JpaRepository<Product, Long> {

	Product findById(long id);
	ArrayList<Product> findAllBySubcategory(Subcategory subcategory);
}
