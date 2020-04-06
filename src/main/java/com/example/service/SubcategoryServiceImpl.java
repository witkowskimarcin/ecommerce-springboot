package com.example.service;

import com.example.entity.Category;
import com.example.entity.Subcategory;
import com.example.model.SubcategoryModel;
import com.example.repository.CategoryRepository;
import com.example.repository.SubcategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubcategoryServiceImpl implements SubcategoryService
{
    private SubcategoryRepository subcategoryRepository;
    private CategoryRepository categoryRepository;
    private Mappers mappers;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public SubcategoryServiceImpl(SubcategoryRepository subcategoryRepository, CategoryRepository categoryRepository, Mappers mappers)
    {
        this.subcategoryRepository = subcategoryRepository;
        this.categoryRepository = categoryRepository;
        this.mappers = mappers;
    }

    @Override
    public List<SubcategoryModel> getAllSubcategoriesByCategoryId(Long id)
    {
        return categoryRepository
                .findById(id)
                .orElseThrow(()->new RuntimeException("Category id: "+id+" does not exist"))
                .getSubcategories()
                .stream()
                .map(mappers::mapSubcategoryEntityToModel)
                .collect(Collectors.toList());
    }

    @Override
    public SubcategoryModel getSubcategoryById(Long id)
    {
        return mappers.mapSubcategoryEntityToModel(subcategoryRepository
                .findById(id)
                .orElseThrow(()->new RuntimeException("Subcategory id: "+id+" does not exist")));
    }

    @Override
    public void addSubcategory(Long id, SubcategoryModel subcategory)
    {
        Category c = categoryRepository.findById(id).orElseThrow(()->new RuntimeException("Category id: "+id+" does not exist"));
        Subcategory s = mappers.mapSubcategoryModelToEntity(subcategory);
        s.setCategory(c);
        subcategoryRepository.save(s);
        c.getSubcategories().add(s);
        categoryRepository.save(c);
        logger.info("Add subcategory id: "+s.getId());
    }

    @Override
    public void editSubcategory(Long id, SubcategoryModel subcategory)
    {
        Subcategory s = subcategoryRepository
                .findById(id)
                .orElseThrow(()->new RuntimeException("Subcategory id: "+id+" does not exist"));

        s.setName(subcategory.getName());
        subcategoryRepository.save(s);
        logger.info("Edit subcategory id: "+s.getId());
    }

    @Override
    public void removeSubcategory(Long id)
    {
        subcategoryRepository.deleteById(id);
        logger.info("Remove subcategory id: "+id);
    }
}
