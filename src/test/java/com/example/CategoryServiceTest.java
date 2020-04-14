package com.example;

import com.example.entity.Category;
import com.example.model.CategoryModel;
import com.example.repository.CategoryRepository;
import com.example.service.CategoryService;
import com.example.service.Mappers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceTest
{
    @MockBean private CategoryRepository categoryRepository;
    @Autowired private CategoryService categoryService;

    @Test
    public void getAllCategoriesTest(){
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(prepareCategoryEntity()));
        List<CategoryModel> result = categoryService.getAllCategories();
        assertEquals(1, result.size());
    }

    @Test
    public void getCategoryByIdTest(){
        when(categoryRepository.findById(1L)).thenReturn(java.util.Optional.of(prepareCategoryEntity()));
        CategoryModel result = categoryService.getCategoryById(1L);
        System.out.println(result.getId());
        assertEquals(1L, result.getId());
    }

    @Test
    public void addCategoryTest(){
        categoryService.addCategory(prepareCategoryModel());
        verify(categoryRepository).save(prepareCategoryEntity());
        atLeastOnce();
    }

    @Test
    public void editCategoryTest(){
        when(categoryRepository.findById(1L)).thenReturn(java.util.Optional.of(prepareCategoryEntity()));
        categoryService.editCategory(1L, prepareCategoryModel());
        verify(categoryRepository).save(prepareCategoryEntity());
        atLeastOnce();
    }

    @Test
    public void removeCategoryByIdTest(){
        categoryService.removeCategoryById(1L);
        verify(categoryRepository).deleteById(1L);
        atLeastOnce();
    }

    private Category prepareCategoryEntity()
    {
        Category c = new Category();
        c.setId(1L);
        c.setName("TestCategory");
        c.setSubcategories(null);
        return c;
    }

    private CategoryModel prepareCategoryModel()
    {
        CategoryModel c = new CategoryModel();
        c.setId(1L);
        c.setName("TestCategory");
        c.setSubcategories(null);
        return c;
    }
}
