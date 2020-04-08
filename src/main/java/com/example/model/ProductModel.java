package com.example.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class ProductModel {

	@NotNull
	private long id;

	@NotNull
	@Size(min=3, max = 50)
	private String name;

	@Size(max = 5000)
	private String description;

	@NotNull
	private double price;

	@NotNull
	private List<ImageModel> images;

    private SubcategoryModel subcategory;

	@NotNull
	private int quantity;
}
