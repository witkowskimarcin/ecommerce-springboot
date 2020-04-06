package com.example.service;

import com.example.entity.*;
import com.example.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Mappers
{
    private ModelMapper mapper;

    public Mappers(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public CategoryModel mapCategoryEntityToModel(Category c){
        return mapper.map(c, CategoryModel.class);
    }

    public Category mapCategoryModelToEntity(CategoryModel c){
        return mapper.map(c, Category.class);
    }

    public SubcategoryModel mapSubcategoryEntityToModel(Subcategory c){
        return mapper.map(c, SubcategoryModel.class);
    }

    public Subcategory mapSubcategoryModelToEntity(SubcategoryModel c){
        return mapper.map(c, Subcategory.class);
    }

    public ImageModel mapImageEntityToModel(Image i){
        return mapper.map(i, ImageModel.class);
    }

    public Image mapImageModelToEntity(ImageModel i){
        return mapper.map(i, Image.class);
    }

    public OpportunityModel mapOpportunityEntityToModel(Opportunity o) {
        return mapper.map(o, OpportunityModel.class);
    }

    public Opportunity mapOpportunityModelToEntity(OpportunityModel o) {
        return mapper.map(o, Opportunity.class);
    }

    public OrderModel mapOrderEntityToModel(Order o) {
        return mapper.map(o, OrderModel.class);
    }

    public Order mapOrderModelToEntity(OrderModel o) {
        return mapper.map(o, Order.class);
    }

    public OrderDetailModel mapOrderDetailEntityToModel(OrderDetail o) {
        return mapper.map(o, OrderDetailModel.class);
    }

    public OrderDetail mapOrderDetailModelToEntity(OrderDetailModel o) {
        return mapper.map(o, OrderDetail.class);
    }

    public UserModel mapUserEntityToModel(User u) {
        return mapper.map(u, UserModel.class);
    }

    public User mapUserModelToEntity(UserModel u) {
        return mapper.map(u, User.class);
    }

    public ProductModel mapProductEntityToModel(Product p) {
        return mapper.map(p, ProductModel.class);
    }

    public Product mapProductModelToEntity(ProductModel p) {
        return mapper.map(p, Product.class);
    }

    public PromotedProductModel mapPromotedProductEntityToModel(PromotedProduct pp) {
        return mapper.map(pp, PromotedProductModel.class);
    }

    public PromotedProduct mapPromotedProductModelToEntity(PromotedProductModel pp) {
        return mapper.map(pp, PromotedProduct.class);
    }
}
