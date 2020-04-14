package com.example.service;

import com.example.entity.Image;
import com.example.entity.Product;
import com.example.model.*;

import java.util.List;

public interface ProductService
{
    ProductModel getProductById(Long id);
    List<ProductModel> getProductsBySubcategoryId(Long id);
    void addProduct(Long id, ProductModel product);
    void editProduct(ProductModel product);
    void removeProduct(Long id);
    CategoryModel getCategoryOfProduct(Long id);
    SubcategoryModel getSubcategoryOfProduct(Long id);
}
