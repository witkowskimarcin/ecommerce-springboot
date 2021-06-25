package com.example.test;

import com.example.CommonResources;
import com.example.entity.*;
import com.example.model.*;
import com.example.repository.*;
import com.example.service.ImageService;
import com.example.service.OpportunityService;
import com.example.service.ProductService;
import com.example.service.PromotedProductService;
import io.cucumber.java.en.Then;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PromotedProductRepository promotedProductRepository;
    @Autowired
    private OpportunityRepository opportunityRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private SubcategoryRepository subcategoryRepository;

    @Autowired
    private ProductService productService;
    @Autowired
    private PromotedProductService promotedProductService;
    @Autowired
    private OpportunityService opportunityService;
    @Autowired
    private ImageService imageService;

    @Then("productService.getProductById")
    public void getProductByIdTest() {
        ProductModel result = productService.getProductById(1L);
        assertNotNull(result.getName());
    }

    @Then("productService.getProductsBySubcategoryId")
    public void getProductsBySubcategoryIdTest() {
        List<ProductModel> result = productService.getProductsBySubcategoryId(1L);
        assertEquals(1, result.size());
    }

    @Test(expected = UnsupportedOperationException.class)
    @Then("productService.addProduct")
    public void addProductTest() {
        productService.addProduct(1L, CommonResources.productModel);
        verify(productRepository, atLeastOnce()).save(CommonResources.productEntity);
    }


    @Then("productService.editProduct")
    public void editProductTest() {
        productService.editProduct(CommonResources.productModel);
        verify(productRepository, atLeastOnce()).save(CommonResources.productEntity);
    }


    @Then("productService.removeProduct")
    public void removeProductTest() {
        productService.removeProduct(1L);
        verify(productRepository, atLeastOnce()).deleteById(1L);
    }


    @Then("imageService.saveImage")
    public void saveImageTest() {
        imageService.saveImage(CommonResources.imageModel);
        verify(imageRepository, atLeastOnce()).save(CommonResources.imageEntity);
    }


    @Then("imageService.saveImages")
    public void saveImagesTest() {
        imageService.saveImages(Arrays.asList(CommonResources.imageModel));
        verify(imageRepository, atLeastOnce()).saveAll(any());
    }


    @Then("productService.getCategoryOfProduct")
    public void getCategoryOfProductTest() {
        CategoryModel result = productService.getCategoryOfProduct(1L);
        assertNotNull(result.getName());
    }


    @Then("productService.getSubcategoryOfProduct")
    public void getSubcategoryOfProductTest() {
        SubcategoryModel result = productService.getSubcategoryOfProduct(1L);
        assertNotNull(result.getName());
    }

    @Then("opportunityService.getOpportunity")
    public void getOpportunityTest() {
        OpportunityModel result = opportunityService.getOpportunity();
        assertNotNull(result.getPromotionCode());
    }


    @Then("opportunityService.setOpportunity")
    public void setOpportunityTest() {
        opportunityService.setOpportunity(CommonResources.opportunityModel);
        verify(opportunityRepository, atLeastOnce()).save(any());

    }


    @Then("opportunityService.unsetOpportunity")
    public void unsetOpportunityTest() {
        opportunityService.unsetOpportunity();
        verify(opportunityRepository, atLeastOnce()).deleteAll();

    }


    @Then("promotedProductService.addPromotedProduct")
    public void addPromotedProductTest() {
        promotedProductService.addPromotedProduct(1L);
        verify(promotedProductRepository, atLeastOnce()).save(any());

    }


    @Then("promotedProductService.removePromotedProduct")
    public void removePromotedProductTest() {
        promotedProductService.removePromotedProduct(1L);
        verify(promotedProductRepository, atLeastOnce()).deleteById(1L);
    }


    @Then("promotedProductService.getAllPromotedProducts")
    public void getAllPromotedProductsTest() {
        List<PromotedProductModel> result = promotedProductService.getAllPromotedProducts();
        assertEquals(1, result.size());
    }
}
