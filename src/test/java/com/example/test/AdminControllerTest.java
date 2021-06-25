package com.example.test;

import com.example.CommonResources;
import com.example.controller.AdminController;
import com.example.model.CategoryModel;
import com.example.model.OpportunityModel;
import com.example.model.ProductModel;
import com.example.model.SubcategoryModel;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;

public class AdminControllerTest {
    @Autowired
    private AdminController adminController;

    private CategoryModel category = CommonResources.categoryModel;
    private SubcategoryModel subcategory = CommonResources.subcategoryModel;
    private ProductModel product = CommonResources.productModel;
    private OpportunityModel opportunity = CommonResources.opportunityModel;

    @Then("addCategoryTest")
    public void addCategoryTest() {
        ResponseEntity response = adminController.addCategory(category);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Then("editCategoryTest")
    public void editCategoryTest() {
        ResponseEntity response = adminController.editCategory(1L, category);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Then("removeCategoryTest")
    public void removeCategoryTest() {
        ResponseEntity response = adminController.removeCategory(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Then("addSubcategoryTest")
    public void addSubcategoryTest() {
        ResponseEntity response = adminController.addSubcategory(1L, subcategory);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Then("editSubcategoryTest")
    public void editSubcategoryTest() {
        ResponseEntity response = adminController.editSubcategory(1L, subcategory);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Then("removeSubcategoryTest")
    public void removeSubcategoryTest() {
        ResponseEntity response = adminController.removeSubcategory(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Then("addProductTest")
    public void addProductTest() {
        ResponseEntity response = adminController.addProduct(1L, product);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Then("editProductTest")
    public void editProductTest() {
        ResponseEntity response = adminController.editProduct(1L, product);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Then("removeProductTest")
    public void removeProductTest() {
        ResponseEntity response = adminController.removeProduct(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Then("promotedProductsAddTest")
    public void promotedProductsAddTest() {
        ResponseEntity response = adminController.promotedProductsAdd(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Then("promotedProductsRemoveTest")
    public void promotedProductsRemoveTest() {
        ResponseEntity response = adminController.promotedProductsRemove(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Then("setOpportunityTest")
    public void setOpportunityTest() {
        ResponseEntity response = adminController.setOpportunity(opportunity);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Then("unsetOpportunityTest")
    public void unsetOpportunityTest() {
        ResponseEntity response = adminController.unsetOpportunity();
        assertEquals(200, response.getStatusCodeValue());
    }

    @Then("ordersTest")
    public void ordersTest() {
        ResponseEntity response = adminController.orders();
        assertEquals(200, response.getStatusCodeValue());
    }

    @Then("orderDetailsTest")
    public void orderDetailsTest() {
        ResponseEntity response = adminController.orderDetails(1L);
        assertEquals(200, response.getStatusCodeValue());
    }
}
