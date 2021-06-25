package com.example;

import com.example.entity.*;
import com.example.model.*;
import com.example.repository.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class CommonResources {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SubcategoryRepository subcategoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PromotedProductRepository promotedProductRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OpportunityRepository opportunityRepository;

    public static Category categoryEntity;
    public static CategoryModel categoryModel;
    public static Subcategory subcategoryEntity;
    public static SubcategoryModel subcategoryModel;
    public static ProductModel productModel;
    public static Product productEntity;
    public static OpportunityModel opportunityModel;
    public static Opportunity opportunityEntity;
    public static PromotedProductModel promotedProductModel;
    public static PromotedProduct promotedProductEntity;
    public static ImageModel imageModel;
    public static Image imageEntity;
    public static OrderModel orderModel;
    public static Order orderEntity;
    public static OrderDetailModel orderDetailModel;
    public static OrderDetail orderDetailEntity;

    @Given("Prepare category with id: {int}, name: {string}")
    public void prepareCategory(long id, String categoryName) {
        categoryEntity = new Category();
        categoryEntity.setId(id);
        categoryEntity.setName(categoryName);
        categoryEntity.setSubcategories(subcategoryEntity != null ? Arrays.asList(subcategoryEntity) : new ArrayList<>());

        categoryModel = new CategoryModel();
        categoryModel.setId(id);
        categoryModel.setName(categoryName);
        categoryModel.setSubcategories(subcategoryModel != null ? Arrays.asList(subcategoryModel) : new ArrayList<>());
    }

    @Given("Prepare subcategory with id: {int}, name: {string}")
    public void prepareSubcategory(long id, String subcategoryName) {
        subcategoryEntity = new Subcategory();
        subcategoryEntity.setId(id);
        subcategoryEntity.setName(subcategoryName);
        List<Product> productList = new ArrayList<>();
        if (productEntity != null) productList.add(productEntity);
        subcategoryEntity.setProducts(productList);

        subcategoryModel = new SubcategoryModel();
        subcategoryModel.setId(id);
        subcategoryModel.setName(subcategoryName);
        List<ProductModel> productListModel = new ArrayList<>();
        if (productEntity != null) productListModel.add(productModel);
        subcategoryModel.setProducts(productListModel);
    }

    @Given("Prepare product with id: {int}, name: {string}, description: {string}, price: {double}, quantity: {int}")
    public void prepareProduct(long id, String productName, String description, double price, int quantity) {
        productModel = new ProductModel();
        productModel.setId(id);
        productModel.setName(productName);
        productModel.setDescription(description);
        productModel.setPrice(price);
        productModel.setQuantity(quantity);
        productModel.setSubcategory(subcategoryModel);
        productModel.setImages(imageModel != null ? Arrays.asList(imageModel) : new ArrayList<>());

        productEntity = new Product();
        productEntity.setId(id);
        productEntity.setName(productName);
        productEntity.setDescription(description);
        productEntity.setPrice(price);
        productEntity.setQuantity(quantity);
        productEntity.setSubcategory(subcategoryEntity);
        productEntity.setImages(imageEntity != null ? Arrays.asList(imageEntity) : new ArrayList<>());
    }

    @Given("Prepare opportunity with id: {int}, quantity: {int}, promotionCode: {string}")
    public void prepareOpportunity(long id, int quantity, String promotionCode) {
        opportunityModel = new OpportunityModel();
        opportunityModel.setId(id);
        opportunityModel.setQuantity(quantity);
        opportunityModel.setPromotionCode(promotionCode);
        opportunityModel.setProduct(productModel);

        opportunityEntity = new Opportunity();
        opportunityEntity.setId(id);
        opportunityEntity.setQuantity(quantity);
        opportunityEntity.setPromotionCode(promotionCode);
        opportunityEntity.setProduct(productEntity);
    }

    @Given("Prepare promotedProduct with id: {int}")
    public void preparePromotedProduct(long id) {
        promotedProductModel = new PromotedProductModel();
        promotedProductModel.setId(id);
        promotedProductModel.setProduct(productModel);

        promotedProductEntity = new PromotedProduct();
        promotedProductEntity.setId(id);
        promotedProductEntity.setProduct(productEntity);
    }

    @Given("Prepare image with id: {int}, image: {string}")
    public void prepareImage(long id, String image) {
        imageModel = new ImageModel();
        imageModel.setId(id);
        imageModel.setImage(image);

        imageEntity = new Image();
        imageEntity.setId(id);
        imageEntity.setImageBase64(image);
    }

    @Given("Prepare order with id: {int}, date: {string}, firstname: {string}, lastname: {string}, locality: {string}, street: {string}, zipCode: {string}, phone: {string}, shipment: {int}, description: {string}")
    public void prepareOrder(long id, String date, String firstname, String lastname,
                             String locality, String street, String zipCode, String phone, int shipment, String description) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date d = formatter.parse(date);

        orderModel = new OrderModel();
        orderModel.setId(id);
        orderModel.setDate(d);
        orderModel.setFirstname(firstname);
        orderModel.setLastname(lastname);
        orderModel.setLocality(locality);
        orderModel.setStreet(street);
        orderModel.setZipCode(zipCode);
        orderModel.setPhone(phone);
        orderModel.setShipment(shipment);
        orderModel.setDescription(description);
        orderModel.setOrderDetails(orderDetailModel != null ? Arrays.asList(orderDetailModel) : new ArrayList<>());

        orderEntity = new Order();
        orderEntity.setId(id);
        orderEntity.setDate(d);
        orderEntity.setFirstname(firstname);
        orderEntity.setLastname(lastname);
        orderEntity.setLocality(locality);
        orderEntity.setStreet(street);
        orderEntity.setZipCode(zipCode);
        orderEntity.setPhone(phone);
        orderEntity.setShipment(shipment);
        orderEntity.setDescription(description);
        orderEntity.setOrderDetails(orderDetailEntity != null ? Arrays.asList(orderDetailEntity) : new ArrayList<>());
    }

    @Given("Prepare order detail with id: {int}, quantity: {int}")
    public void prepareImage(long id, int quantity) {
        orderDetailModel = new OrderDetailModel();
        orderDetailModel.setId(id);
        orderDetailModel.setQuantity(quantity);
        orderDetailModel.setProduct(productModel);

        orderDetailEntity = new OrderDetail();
        orderDetailEntity.setId(id);
        orderDetailEntity.setQuantity(quantity);
        orderDetailEntity.setProduct(productEntity);
    }

    @When("categoryRepository.findAll")
    public void categoryRepositoryFindAll() {
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(categoryEntity));
    }

    @When("categoryRepository.findById")
    public void categoryRepositoryFindById() {
        categoryEntity.setSubcategories(Arrays.asList(subcategoryEntity));
        when(categoryRepository.findById(anyLong())).thenReturn(java.util.Optional.of(categoryEntity));
    }

    @When("subcategoryRepository.findById")
    public void whenGetSubcategoryById() {
        subcategoryEntity.setCategory(categoryEntity);
        when(subcategoryRepository.findById(anyLong())).thenReturn(java.util.Optional.of(subcategoryEntity));
    }

    @When("subcategoryRepository.findAll")
    public void subcategoryRepositoryFindAll() {
        when(subcategoryRepository.findAll()).thenReturn(Arrays.asList(subcategoryEntity));
    }

    @When("productRepository.findById")
    public void productRepositoryFindById() {
        when(productRepository.findById(anyLong())).thenReturn(java.util.Optional.of(productEntity));
    }

    @When("productRepository.findAllBySubcategory")
    public void productRepositoryFindAllBySubcategory() {
        when(productRepository.findAllBySubcategory(any())).thenReturn(Arrays.asList(productEntity));
    }

    @When("opportunityRepository.findAll")
    public void opportunityRepositoryFindAll() {
        when(opportunityRepository.findAll()).thenReturn(Arrays.asList(opportunityEntity));
    }

    @When("promotedProductRepository.findAll")
    public void promotedProductRepositoryFindAll() {
        when(promotedProductRepository.findAll()).thenReturn(Arrays.asList(promotedProductEntity));
    }

    @When("orderRepository.findAll")
    public void orderRepositoryFindAll() {
        when(orderRepository.findAll()).thenReturn(Arrays.asList(orderEntity));
    }

    @When("orderRepository.findById")
    public void orderRepositoryFindById() {
        when(orderRepository.findById(anyLong())).thenReturn(java.util.Optional.of(orderEntity));
    }
}
