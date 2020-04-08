package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class SubcategoryModel {

    @NotNull
    private long id;

    @NotNull
    @Size(min=3, max = 50)
    private String name;

    @JsonIgnore
    private CategoryModel category;

    @JsonIgnore
    private List<ProductModel> products;
}
