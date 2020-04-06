package com.example.repository;

import com.example.entity.PromotedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("promotedProductRepository")
public interface PromotedProductRepository extends JpaRepository<PromotedProduct, Long> {

	PromotedProduct findById(long id);
}
