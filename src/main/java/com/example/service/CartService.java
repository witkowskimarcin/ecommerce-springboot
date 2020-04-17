package com.example.service;

import com.example.model.CartModel;

import javax.servlet.http.HttpSession;

public interface CartService
{
    void addProductToCart(Long id);
    CartModel getCart();
    void incrementAmountOfProduct(Long id);
    void decrementAmountOfProduct(Long id);
    void clearCart();
    CartModel getCartInSession(HttpSession session);
    Integer getQuantity();
}
