package com.example;

import com.example.controller.AdminController;
import com.example.model.*;
import com.example.service.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminControllerTest
{
    @MockBean private CategoryService categoryService;
    @MockBean private SubcategoryService subcategoryService;
    @MockBean private ProductService productService;
    @MockBean private PromotedProductService promotedProductService;
    @MockBean private OpportunityService opportunityService;
    @MockBean private OrderService orderService;
    @MockBean private OrderDetailService orderDetailService;

    @Autowired private AdminController adminController;

    @Test
    public void addCategoryTest(){
        CategoryModel c = prepareCategory();
        ResponseEntity response = adminController.addCategory(c);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void editCategoryTest(){
        CategoryModel c = prepareCategory();
        ResponseEntity response = adminController.editCategory(1L, c);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void removeCategoryTest(){
        ResponseEntity response = adminController.removeCategory(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void addSubcategoryTest(){
        SubcategoryModel c = prepareSubcategory();
        ResponseEntity response = adminController.addSubcategory(1L, c);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void editSubcategoryTest() {
        SubcategoryModel c = prepareSubcategory();
        ResponseEntity response = adminController.editSubcategory(1L, c);
        assertEquals(200, response.getStatusCodeValue());
    }
    @Test
    public void removeSubcategoryTest(){
        ResponseEntity response = adminController.removeSubcategory(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void addProductTest(){
        ProductModel c = prepareProduct();
        ResponseEntity response = adminController.addProduct(1L, c);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void editProductTest(){
        ProductModel c = prepareProduct();
        ResponseEntity response = adminController.editProduct(1L, c);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void removeProductTest(){
        ResponseEntity response = adminController.removeProduct(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void promotedProductsAddTest(){
        ResponseEntity response = adminController.promotedProductsAdd(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void promotedProductsRemoveTest(){
        ResponseEntity response = adminController.promotedProductsRemove(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void setOpportunityTest(){
        OpportunityModel c = prepareOpportunity();
        ResponseEntity response = adminController.setOpportunity(c);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void unsetOpportunityTest(){
        ResponseEntity response = adminController.unsetOpportunity();
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void ordersTest(){
//TODO
    }

    @Test
    public void orderDetailsTest(){
//TODO
    }

    private CategoryModel prepareCategory()
    {
        CategoryModel c = new CategoryModel();
        c.setId(1l);
        c.setName("TestCategory");
        c.setSubcategories(null);
        return c;
    }

    private SubcategoryModel prepareSubcategory()
    {
        SubcategoryModel c = new SubcategoryModel();
        c.setId(1l);
        c.setName("TestSubcategory");
        c.setCategory(prepareCategory());
        c.setProducts(null);
        return c;
    }

    private ProductModel prepareProduct()
    {
        ProductModel p = new ProductModel();
        p.setId(1l);
        p.setName("TestProduct");
        p.setDescription("TestDescription");
        p.setPrice(1.0);
        p.setQuantity(1);
        p.setSubcategory(prepareSubcategory());
        p.setImages(null);
        return p;
    }

    private OpportunityModel prepareOpportunity()
    {
        OpportunityModel p = new OpportunityModel();
        p.setId(1l);
        p.setQuantity(1);
        p.setPromotionCode("TEST");
        p.setProduct(prepareProduct());
        return p;
    }
}
