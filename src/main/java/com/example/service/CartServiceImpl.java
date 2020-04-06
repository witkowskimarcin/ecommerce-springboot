package com.example.service;

import com.example.entity.Product;
import com.example.model.CartModel;
import com.example.repository.ProductRepository;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class CartServiceImpl implements CartService
{
    private HttpSession session;
    private ProductRepository productRepository;
    private Mappers mappers;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public CartServiceImpl(HttpSession session, ProductRepository productRepository, Mappers mappers)
    {
        this.session = session;
        this.productRepository = productRepository;
        this.mappers = mappers;
    }

    @Override
    public void addProductToCart(Long id)
    {
        CartModel cartModel = CartModel.getCartInSession(session);
        cartModel.addProduct(productRepository.findById(id).orElseThrow(
                ()->new RuntimeException("Product id: "+id+" does not exist")));
        logger.info("Product id: "+id+" has been added to cart");
    }

    @Override
    public CartModel getCart()
    {
        return CartModel.getCartInSession(session);
    }

    @Override
    public void incrementAmountOfProduct(Long id)
    {
        CartModel cartModel = CartModel.getCartInSession(session);
        Product product = productRepository.findById(id).orElseThrow(
                ()->new RuntimeException("Product id: "+id+" does not exist"));
        cartModel.addProduct(product);
        logger.info("Product id: "+id+" has been incremented in cart");
    }

    @Override
    public void decrementAmountOfProduct(Long id)
    {
        CartModel cartModel = CartModel.getCartInSession(session);
        Product product = productRepository.findById(id).orElseThrow(
                ()->new RuntimeException("Product id: "+id+" does not exist"));
        cartModel.removeProduct(product);
        logger.info("Product id: "+id+" has been incremented in cart");
    }

    @Override
    public void clearCart()
    {
        CartModel cartModel = CartModel.getCartInSession(session);
        cartModel.removeCart();
        logger.info("Cart has been cleared");
    }
}
