package com.example.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Data
public class ProductModel {

	@NotNull
	private long id;

	@NotNull
	@Size(min=3, max = 50)
	private String name;

	private String description;

	@NotNull
	private double price;

	@NotNull
	private List<ImageModel> images;

    private SubcategoryModel subcategory;

	@NotNull
	private int quantity;

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ProductModel that = (ProductModel) o;
		return id == that.id;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(id);
	}
}
