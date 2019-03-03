package com.example.model;

import javax.persistence.*;

@Entity
@Table(name = "order_details")
public class OrderDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
	
	@OneToOne
//	@Transient
	private Product product;
	
	private int quantity;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
