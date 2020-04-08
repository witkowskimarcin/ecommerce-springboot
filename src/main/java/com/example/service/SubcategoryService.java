package com.example.service;

import com.example.model.CategoryModel;
import com.example.model.SubcategoryModel;

import java.util.List;

public interface SubcategoryService
{
    List<SubcategoryModel> getAllSubcategoriesByCategoryId(Long id);
    SubcategoryModel getSubcategoryById(Long sid);
    void addSubcategory(Long id, SubcategoryModel subcategory);
    void editSubcategory(Long id, SubcategoryModel subcategory);
    void removeSubcategory(Long id);
    CategoryModel getCategoryBySubcategoryId(Long id);
}
