package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.json.JSONPropertyIgnore;

import javax.persistence.*;
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

	@Size(max = 500)
	private String description;

	@NotNull
	private double price;

	@NotNull
	private List<ImageModel> images;

    private SubcategoryModel subcategory;

	@NotNull
	private int quantity;
}
