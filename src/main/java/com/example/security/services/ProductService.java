package com.example.security.services;


import com.example.model.Product;

public interface ProductService {

    boolean delete(Product product);
    boolean deleteById(long id);
}
