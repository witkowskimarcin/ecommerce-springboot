package com.example;

import com.example.repository.*;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpSession;

@SpringBootTest
@CucumberContextConfiguration
public class CucumberSpringConfiguration {

    @MockBean
    private CategoryRepository categoryRepository;
    @MockBean
    private SubcategoryRepository subcategoryRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private ImageRepository imageRepository;
    @MockBean
    private ProductRepository productRepository;
    @MockBean
    private PromotedProductRepository promotedProductRepository;
    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private OpportunityRepository opportunityRepository;
    @MockBean
    private Authentication auth;
}