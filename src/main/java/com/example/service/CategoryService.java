package com.example.service;

import com.example.model.CategoryModel;

import java.util.List;

public interface CategoryService
{

    List<CategoryModel> getAllCategories();
    CategoryModel getCategoryById(Long id);
    void addCategory(CategoryModel category);
    void editCategory(Long id, CategoryModel category);
    void removeCategoryById(Long id);
}
