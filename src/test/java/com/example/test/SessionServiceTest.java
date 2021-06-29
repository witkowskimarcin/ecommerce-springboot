package com.example.test;

import com.example.CommonResources;
import com.example.model.CartModel;
import com.example.repository.ProductRepository;
import com.example.repository.UserRepository;
import com.example.service.SessionServiceImpl;
import com.example.service.UserService;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SessionServiceTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private Authentication auth;

    @Autowired
    private UserService userService;

    @Autowired
    private SessionServiceImpl sessionService;

    private HttpSession session;

    private CartModel cartModel;

    @Before
    public void before() {
        session = mock(HttpSession.class);
        sessionService = new SessionServiceImpl(session, productRepository);
    }

    @Given("Prepare cart")
    public void prepareCart() {
        cartModel = new CartModel();
    }

    @When("session.getAttribute")
    public void sessionGetAttribute() {
        when(session.getAttribute("myCart")).thenReturn(cartModel);
    }

    @When("session.getId")
    public void sessionGetId() {
        when(session.getId()).thenReturn("sessionId");
    }

    @Then("sessionService.getCartInSession")
    public void getCartInSession() {
        CartModel result = sessionService.getCartInSession(session);
        assertNotNull(result);
    }

    @Then("sessionService.getCart")
    public void getCart() {
        CartModel result = sessionService.getCart();
        assertNotNull(result);
    }

    @Then("sessionService.getSessionId")
    public void getSessionId() {
        String result = sessionService.getSessionId();
        assertEquals(result, "sessionId");
    }

    @Then("sessionService.getQuantity")
    public void getQuantity() {
        int result = sessionService.getQuantity();
        assertTrue(result > 0);
    }

    @Then("sessionService.addProduct")
    public void addProduct() {
        sessionService.addProduct(cartModel, CommonResources.productEntity);
        assertTrue(cartModel.getProducts().size() > 0);
    }

    @Then("sessionService.clearCart")
    public void clearCart() {
        sessionService.clearCart();
        assertEquals(0, (int) sessionService.getQuantity());
    }

    @Then("sessionService.removeCart")
    public void removeCart() {
        sessionService.removeCart();
        assertEquals(0, (int) sessionService.getQuantity());
    }

    @Then("sessionService.addProductToCart")
    public void addProductToCart() {
        sessionService.addProductToCart(1L);
        assertEquals(1, (int) sessionService.getQuantity());
    }

    @Then("sessionService.removeProduct")
    public void removeProduct() {
        sessionService.removeProduct(cartModel, CommonResources.productEntity);
        assertEquals(1, (int) sessionService.getQuantity());
    }
}
