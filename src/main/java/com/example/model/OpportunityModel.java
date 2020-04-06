package com.example.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class OpportunityModel{

	@NotNull
	private long id;

	@NotNull
	@Size(min = 1, max = 20)
	private String promotionCode;

	@NotNull
	private Integer quantity;

	@NotNull
	private ProductModel product;
}
