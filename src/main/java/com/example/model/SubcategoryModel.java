package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubcategoryModel that = (SubcategoryModel) o;
        return id == that.id;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id);
    }
}
