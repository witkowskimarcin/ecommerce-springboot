package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "products")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "name", length = 255, nullable = false)
	private String name;

	@Column(name = "description", nullable = true, length = 65535, columnDefinition="TEXT")
	private String description;

	@Column(name = "price", nullable = false)
	private double price;

	@OneToMany
	private List<Image> images;
	
    @ManyToOne
    @JoinColumn(name = "subcategory_id", nullable = false)
    private Subcategory subcategory;

    private int quantity;

	public Product() {
	}

	public String getName() {
		return name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Product product = (Product) o;
		return id == product.id;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(id);
	}

	@JsonIgnore
	public Subcategory getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(Subcategory subcategory) {
		this.subcategory = subcategory;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	//	public String getImagePath() {
//		return imagePath;
//	}
//
//	public void setImagePath(String imagePath) {
//		this.imagePath = imagePath;
//	}
//
//	public int getImageCount() {
//		return imageCount;
//	}
//
//	public void setImageCount(int imageCount) {
//		this.imageCount = imageCount;
//	}


}
