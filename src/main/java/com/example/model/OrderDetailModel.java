package com.example.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
public class OrderDetailModel {

	@NotNull
    private long id;

	@NotNull
	private ProductModel product;

	@NotNull
	private int quantity;
}
