package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CategoryModel {

    @NotNull
    private long id;

    @NotNull
    @Size(min = 4,max = 50)
    private String name;

    private List<SubcategoryModel> subcategories;
}
