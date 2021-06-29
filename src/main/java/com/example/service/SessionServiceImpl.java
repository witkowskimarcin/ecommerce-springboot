package com.example.service;

import com.example.entity.Product;
import com.example.exception.ResourceNotFoundException;
import com.example.model.CartModel;
import com.example.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class SessionServiceImpl implements SessionService
{
    private HttpSession session;
    private ProductRepository productRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public SessionServiceImpl(HttpSession session, ProductRepository productRepository)
    {
        this.session = session;
        this.productRepository = productRepository;
    }

    @Override
    public void addProductToCart(Long id)
    {
        CartModel cartModel = getCartInSession(session);
        addProduct(cartModel, productRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Product id: "+id+" does not exist")));
        logger.info("Product id: "+id+" has been added to cart");
    }

    @Override
    public CartModel getCart()
    {
        return getCartInSession(session);
    }

    @Override
    public void incrementAmountOfProduct(Long id)
    {
        CartModel cartModel = getCartInSession(session);
        Product product = productRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Product id: "+id+" does not exist"));
        addProduct(cartModel, product);
        logger.info("Product id: "+id+" has been incremented in cart");
    }

    @Override
    public void decrementAmountOfProduct(Long id)
    {
        CartModel cartModel = getCartInSession(session);
        Product product = productRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Product id: "+id+" does not exist"));
        removeProduct(cartModel, product);
        logger.info("Product id: "+id+" has been incremented in cart");
    }

    @Override
    public void clearCart()
    {
        CartModel cartModel = getCartInSession(session);
        removeCart();
        logger.info("Cart has been cleared");
    }

    public void addProduct(CartModel c, Product product) {

        if (c.getProducts().containsKey(product)) {
            Integer value = c.getProducts().get(product) + 1;
            c.getProducts().put(product, value);
        } else {
            c.getProducts().put(product, 1);
        }
    }

    public void removeProduct(CartModel c, Product product) {

        if (c.getProducts().containsKey(product)) {
            if (c.getProducts().get(product) == 1) {
                c.getProducts().remove(product);
            } else {
                Integer value = c.getProducts().get(product) - 1;
                c.getProducts().put(product, value);
            }
        } else {
            logger.error("Remove product from cart ERROR");
        }
    }

    public CartModel getCartInSession(HttpSession session) {
        CartModel cartModel = (CartModel) session.getAttribute("myCart");
        if (cartModel == null) {
            cartModel = new CartModel();
            session.setAttribute("myCart", cartModel);
        }
        return cartModel;
    }

    @Override
    public Integer getQuantity()
    {
        CartModel cartModel = getCartInSession(session);
        return cartModel.getProducts().size();
    }

    @Override
    public String getSessionId()
    {
        return session.getId();
    }

    public void removeCart() {
        CartModel cartModel = getCartInSession(session);
        cartModel.setSum(0.0);
        cartModel.getProducts().clear();
    }
}
