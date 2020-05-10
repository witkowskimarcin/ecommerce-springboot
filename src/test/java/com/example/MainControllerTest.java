package com.example;

import com.example.controller.MainController;
import com.example.model.*;
import com.example.service.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MainControllerTest
{
    @MockBean private UserService userService;
    @MockBean private ImageService imageService;
    @MockBean private CategoryService categoryService;
    @MockBean private SubcategoryService subcategoryService;
    @MockBean private ProductService productService;
    @MockBean private PromotedProductService promotedProductService;
    @MockBean private OrderService orderService;
    @MockBean private SessionService sessionService;
    @MockBean private OpportunityService opportunityService;
    @Autowired private MainController mainController;

    @Test
    public void categoriesTest(){
        when(categoryService.getAllCategories()).thenReturn(Arrays.asList(getCategory()));
        ResponseEntity<List<CategoryModel>> response = mainController.categories();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void categoryTest(){
        when(categoryService.getCategoryById(1l)).thenReturn(getCategory());
        ResponseEntity<CategoryModel> response = mainController.category(1l);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1l, response.getBody().getId());
    }

    @Test
    public void subcategoriesTest(){
        when(subcategoryService.getAllSubcategoriesByCategoryId(1l)).thenReturn(Arrays.asList(getSubcategory()));
        ResponseEntity<List<SubcategoryModel>> response = mainController.subcategories(1l);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void subcategoryTest(){
        when(subcategoryService.getSubcategoryById(1l)).thenReturn(getSubcategory());
        ResponseEntity<SubcategoryModel> response = mainController.getSubcategory(1l);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1l, response.getBody().getId());
    }

    @Test
    public void opportunityTest(){
        OpportunityModel o = new OpportunityModel();
        o.setId(1l);
        o.setPromotionCode("TEST01");
        o.setQuantity(1);
        o.setProduct(null);

        when(opportunityService.getOpportunity()).thenReturn(o);
        ResponseEntity<OpportunityModel> response = mainController.opportunity();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1l, response.getBody().getId());
    }

    @Test
    public void promotedproductsTest(){
        PromotedProductModel p = new PromotedProductModel();
        p.setId(1l);
        p.setProduct(null);

        when(promotedProductService.getAllPromotedProducts()).thenReturn(Arrays.asList(p));
        ResponseEntity<List<PromotedProductModel>> response = mainController.promotedproducts();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void getProductsBySubcategoryIdTest(){
        when(productService.getProductsBySubcategoryId(1l)).thenReturn(Arrays.asList(getProduct()));
        ResponseEntity<List<ProductModel>> response = mainController.getProductsBySubcategoryId(1l);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void getCategoryBySubcategoryIdTest(){
        when(subcategoryService.getCategoryBySubcategoryId(1l)).thenReturn(getCategory());
        ResponseEntity<CategoryModel> response = mainController.getCategoryBySubcategoryId(1l);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1l, response.getBody().getId());
    }

    @Test
    public void getProductByIdTest(){
        when(productService.getProductById(1l)).thenReturn(getProduct());
        ResponseEntity<ProductModel> response = mainController.getProductById(1l);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1l, response.getBody().getId());
    }

    @Test
    public void getCategoryOfProductTest(){
        when(productService.getCategoryOfProduct(1l)).thenReturn(getCategory());
        ResponseEntity<CategoryModel> response = mainController.getCategoryOfProduct(1l);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1l, response.getBody().getId());
    }

    private CategoryModel getCategory(){
        CategoryModel c = new CategoryModel();
        c.setId(1l);
        c.setName("TestCategoryName");
        c.setSubcategories(null);
        return c;
    }

    private SubcategoryModel getSubcategory(){
        SubcategoryModel s = new SubcategoryModel();
        s.setId(1l);
        s.setName("TestSubcategoryName");
        s.setCategory(getCategory());
        s.setProducts(null);
        return s;
    }

    private ProductModel getProduct(){
        ProductModel p = new ProductModel();
        p.setId(1l);
        p.setName("TestProductName");
        p.setDescription("TestProductDescription");
        p.setPrice(1.2);
        p.setQuantity(1);
        p.setSubcategory(getSubcategory());
        p.setImages(null);
        return p;
    }
}
