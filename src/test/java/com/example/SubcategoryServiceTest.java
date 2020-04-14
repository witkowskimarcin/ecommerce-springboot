package com.example;

import com.example.entity.Category;
import com.example.entity.Subcategory;
import com.example.model.CategoryModel;
import com.example.model.SubcategoryModel;
import com.example.repository.CategoryRepository;
import com.example.repository.SubcategoryRepository;
import com.example.service.SubcategoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SubcategoryServiceTest
{
    @MockBean private SubcategoryRepository subcategoryRepository;
    @MockBean private CategoryRepository categoryRepository;
    @Autowired private SubcategoryService subcategoryService;

    @Test
    public void getAllSubcategoriesByCategoryIdTest(){
        Category c = new Category();
        c.setId(1L);
        c.setName("TestCategory");
        c.setSubcategories(Arrays.asList(prepareSubcategoryEntity()));
        when(categoryRepository.findById(1L)).thenReturn(java.util.Optional.of(c));
        List<SubcategoryModel> result = subcategoryService.getAllSubcategoriesByCategoryId(1L);
        assertEquals(1, result.size());
    }

    @Test
    public void getSubcategoryByIdTest(){
        when(subcategoryRepository.findById(1L)).thenReturn(java.util.Optional.of(prepareSubcategoryEntity()));
        SubcategoryModel result = subcategoryService.getSubcategoryById(1L);
        assertEquals(1, result.getId());
    }

    @Test
    public void addSubcategoryTest(){
        Category c = prepareCategory();
        when(categoryRepository.findById(1L)).thenReturn(java.util.Optional.of(c));
        subcategoryService.addSubcategory(1L, prepareSubcategoryModel());
        verify(subcategoryRepository).save(prepareSubcategoryEntity());
        atLeastOnce();
    }

    @Test
    public void editSubcategoryTest(){
        when(subcategoryRepository.findById(1L)).thenReturn(java.util.Optional.of(prepareSubcategoryEntity()));
        subcategoryService.editSubcategory(1L, prepareSubcategoryModel());
        verify(subcategoryRepository).save(prepareSubcategoryEntity());
        atLeastOnce();
    }

    @Test
    public void removeSubcategoryTest(){
        subcategoryService.removeSubcategory(1L);
        verify(subcategoryRepository).deleteById(1L);
        atLeastOnce();
    }

    @Test
    public void getCategoryBySubcategoryIdTest() {
        when(subcategoryRepository.findById(1L)).thenReturn(java.util.Optional.of(prepareSubcategoryEntity()));
        CategoryModel result = subcategoryService.getCategoryBySubcategoryId(1L);
        assertEquals(1, result.getId());
    }

    private Subcategory prepareSubcategoryEntity() {
        Subcategory c = new Subcategory();
        c.setId(1L);
        c.setName("TestSubcategory");
        c.setCategory(prepareCategory());
        c.setProducts(null);
        return c;
    }

    private SubcategoryModel prepareSubcategoryModel() {
        SubcategoryModel c = new SubcategoryModel();
        c.setId(1L);
        c.setName("TestSubcategory");
        c.setCategory(null);
        c.setProducts(null);
        return c;
    }

    private Category prepareCategory()
    {
        Category c = new Category();
        c.setId(1L);
        c.setName("TestCategory");
        c.setSubcategories(new ArrayList<>());
        return c;
    }
}
