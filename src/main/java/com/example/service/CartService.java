package com.example.service;

import com.example.model.CartModel;

public interface CartService
{
    void addProductToCart(Long id);
    CartModel getCart();
    void incrementAmountOfProduct(Long id);
    void decrementAmountOfProduct(Long id);
    void clearCart();
}
