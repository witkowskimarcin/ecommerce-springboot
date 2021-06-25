package com.example.test;

import com.example.CommonResources;
import com.example.entity.Subcategory;
import com.example.model.CategoryModel;
import com.example.model.SubcategoryModel;
import com.example.repository.CategoryRepository;
import com.example.repository.SubcategoryRepository;
import com.example.service.SubcategoryService;
import io.cucumber.java.en.Then;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SubcategoryServiceTest {
    @Autowired
    private SubcategoryRepository subcategoryRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SubcategoryService subcategoryService;

    Subcategory subcategoryEntity = CommonResources.subcategoryEntity;
    SubcategoryModel subcategoryModel = CommonResources.subcategoryModel;

    @Then("subcategoryService.getAllSubcategoriesByCategoryId")
    public void thenGetAllSubcategoriesByCategoryId() {
        List<SubcategoryModel> result = subcategoryService.getAllSubcategoriesByCategoryId(1L);
        assertEquals(1, result.size());
    }

    @Then("subcategoryService.getSubcategoryById")
    public void thenGetSubcategoryById() {
        SubcategoryModel result = subcategoryService.getSubcategoryById(1L);
        assertNotNull(result.getName());
    }

    @Then("subcategoryService.addSubcategory")
    public void thenAddSubcategory() {
        subcategoryService.addSubcategory(1L, subcategoryModel);
        verify(subcategoryRepository, atLeastOnce()).save(subcategoryEntity);
    }

    @Then("subcategoryService.editSubcategory")
    public void thenEditSubcategory() {
        subcategoryService.editSubcategory(1L, subcategoryModel);
        verify(subcategoryRepository, atLeastOnce()).save(subcategoryEntity);
    }

    @Then("subcategoryService.removeSubcategory")
    public void thenRemoveSubcategory() {
        subcategoryService.removeSubcategory(1L);
        verify(subcategoryRepository, atLeastOnce()).deleteById(1L);

    }

    @Then("subcategoryService.getCategoryBySubcategoryId")
    public void thenGetCategoryBySubcategoryId() {
        CategoryModel result = subcategoryService.getCategoryBySubcategoryId(1L);
        assertNotNull(result.getName());
    }
}
