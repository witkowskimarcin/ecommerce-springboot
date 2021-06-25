package com.example;

import com.example.entity.Category;
import com.example.model.CategoryModel;
import com.example.repository.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;

import static org.mockito.Mockito.when;

@SpringBootTest
@CucumberContextConfiguration
public class CucumberSpringConfiguration {
//    @MockBean
//    private UserService userService;
//    @MockBean
//    private ImageService imageService;
//    @MockBean
//    private CategoryService categoryService;
//    @MockBean
//    private SubcategoryService subcategoryService;
//    @MockBean
//    private ProductService productService;
//    @MockBean
//    private PromotedProductService promotedProductService;
//    @MockBean
//    private OrderService orderService;
//    @MockBean
//    private SessionService sessionService;
//    @MockBean
//    private OpportunityService opportunityService;

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
}