package com.example.test;

import com.example.CommonResources;
import com.example.entity.Category;
import com.example.model.CategoryModel;
import com.example.repository.CategoryRepository;
import com.example.service.CategoryService;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

public class CategoryServiceTest {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryService categoryService;

    Category categoryEntity = CommonResources.categoryEntity;
    CategoryModel categoryModel = CommonResources.categoryModel;

    @Then("categoryService.getAllCategories")
    public void thenGetAllCategories() {
        List<CategoryModel> result = categoryService.getAllCategories();
        assertEquals(1, result.size());
    }

    @Then("categoryService.getCategoryById")
    public void thenGetCategoryById() {
        CategoryModel result = categoryService.getCategoryById(1L);
        assertNotNull(result.getName());
    }

    @Then("categoryService.addCategory")
    public void thenAddCategory() {
        categoryService.addCategory(categoryModel);
        verify(categoryRepository, atLeastOnce()).save(categoryEntity);
    }

    @Then("categoryService.editCategory")
    public void thenEditCategory() {
        categoryService.editCategory(1L, categoryModel);
        verify(categoryRepository, atLeastOnce()).save(categoryEntity);
    }

    @Then("categoryService.removeCategoryById")
    public void thenRemoveCategoryById() {
        categoryService.removeCategoryById(1L);
        verify(categoryRepository, atLeastOnce()).deleteById(1L);
    }
}
