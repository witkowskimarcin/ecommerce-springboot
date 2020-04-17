package com.example.model;
import java.util.HashMap;
import java.util.Map;

import com.example.entity.Product;
import lombok.Data;

@Data
public class CartModel
{
	private Map<Product, Integer> products;
	private double sum;

	public CartModel() {
		products = new HashMap<>();
		sum = 0.0;
	}
}
