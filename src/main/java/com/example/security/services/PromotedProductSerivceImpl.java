package com.example.security.services;

import java.util.ArrayList;

import com.example.repository.PromotedProductRepository;
import com.example.model.Product;
import com.example.model.PromotedProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("promotedProductService")
public class PromotedProductSerivceImpl implements PromotedProductService {
	
	@Autowired
	private PromotedProductRepository promotedProductRepository;

	@Override
	public ArrayList<Product> findAllPromotedProducts (){
		ArrayList<Product> products = new ArrayList<>();
		Iterable<PromotedProduct> promotedProducts = promotedProductRepository.findAll();
		for (PromotedProduct promotedProduct : promotedProducts) {
			//System.out.println(promotedProduct.getProduct().getName());
			products.add(promotedProduct.getProduct());
		}
		return products;
	}
}
