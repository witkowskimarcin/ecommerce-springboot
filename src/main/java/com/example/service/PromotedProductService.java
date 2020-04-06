package com.example.service;

import com.example.entity.PromotedProduct;
import com.example.model.ProductModel;
import com.example.model.PromotedProductModel;

import java.util.List;

public interface PromotedProductService
{
    void addPromotedProduct(PromotedProductModel pp);
    void removePromotedProduct(Long id);
    void deleteById(long id);
    List<PromotedProductModel> getAll();
}
