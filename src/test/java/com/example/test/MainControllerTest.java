package com.example.test;

import com.example.controller.MainController;
import com.example.model.*;
import com.example.service.*;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainControllerTest {
    @Autowired
    private UserService userService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SubcategoryService subcategoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private PromotedProductService promotedProductService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private OpportunityService opportunityService;
    @Autowired
    private MainController mainController;

    @Then("thenGetAllCategories")
    public void thenGetAllCategories() {
        ResponseEntity<List<CategoryModel>> response = mainController.categories();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    @Then("thenGetCategoryById")
    public void thenGetCategoryById() {
        ResponseEntity<CategoryModel> response = mainController.category(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().getName().length() > 0);
    }

    @Then("thenGetAllSubcategoriesByCategoryId")
    public void thenGetAllSubcategoriesByCategoryId() {
        ResponseEntity<List<SubcategoryModel>> response = mainController.subcategories(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    @Then("thenGetSubcategoryById")
    public void thenGetSubcategoryById() {
        ResponseEntity<SubcategoryModel> response = mainController.getSubcategory(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().getName().length() > 0);
    }

    @Then("thenGetOpportunity")
    public void thenGetOpportunity() {
        ResponseEntity<OpportunityModel> response = mainController.opportunity();
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody().getId());
    }

    @Then("thenGetAllPromotedProducts")
    public void thenGetAllPromotedProducts() {
        ResponseEntity<List<PromotedProductModel>> response = mainController.promotedproducts();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    @Then("thenGetProductsBySubcategoryIdTest")
    public void thenGetProductsBySubcategoryIdTest() {
        ResponseEntity<List<ProductModel>> response = mainController.getProductsBySubcategoryId(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    @Then("thenGetCategoryBySubcategoryIdTest")
    public void thenGetCategoryBySubcategoryIdTest() {
        ResponseEntity<CategoryModel> response = mainController.getCategoryBySubcategoryId(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().getName().length() > 0);
    }

    @Then("thenGetProductByIdTest")
    public void thenGetProductByIdTest() {
        ResponseEntity<ProductModel> response = mainController.getProductById(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().getName().length() > 0);
    }

    @Then("thenGetCategoryOfProductTest")
    public void thenGetCategoryOfProductTest() {
        ResponseEntity<CategoryModel> response = mainController.getCategoryOfProduct(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().getName().length() > 0);
    }
}
