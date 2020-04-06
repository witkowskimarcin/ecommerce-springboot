package com.example.service;

import com.example.entity.Category;
import com.example.model.CategoryModel;
import com.example.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService
{
    private CategoryRepository categoryRepository;
    private Mappers mappers;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public CategoryServiceImpl(CategoryRepository categoryRepository, Mappers mappers)
    {
        this.categoryRepository = categoryRepository;
        this.mappers = mappers;
    }

    @Override
    public List<CategoryModel> getAllCategories()
    {
        return categoryRepository
                .findAll()
                .stream()
                .map(mappers::mapCategoryEntityToModel)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryModel getCategoryById(Long id)
    {
        return mappers.mapCategoryEntityToModel(categoryRepository
                .findById(id)
                .orElseThrow(()-> new RuntimeException("Category id: "+id+" does not exist")));
    }

    @Override
    public void addCategory(CategoryModel category)
    {
        categoryRepository.save(mappers.mapCategoryModelToEntity(category));
        logger.info("Category has benn added");
    }

    @Override
    public void editCategory(Long id, CategoryModel category)
    {
        Category c = categoryRepository.findById(id).orElseThrow(
                ()->new RuntimeException("Category id: "+id+" does not exist"));
        c.setName(category.getName());
        categoryRepository.save(c);
        logger.info("Category has benn edited");
    }

    @Override
    public void removeCategoryById(Long id)
    {
        categoryRepository.deleteById(id);
        logger.info("Category has benn removed");
    }
}
