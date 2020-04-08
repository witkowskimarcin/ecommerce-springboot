package com.example.service;

import com.example.model.PromotedProductModel;

import java.util.List;

public interface PromotedProductService
{
    void addPromotedProduct(Long id);
    void removePromotedProduct(Long id);
    void deleteById(long id);
    List<PromotedProductModel> getAll();
}
