package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.json.JSONPropertyIgnore;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SubcategoryModel {

    @NotNull
    private long id;

    @NotNull
    @Size(min=3, max = 50)
    private String name;

    @JsonIgnore
    private CategoryModel category;
    
    private List<ProductModel> products;
}
