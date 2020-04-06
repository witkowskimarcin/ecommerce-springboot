package com.example.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONPropertyIgnore;
import org.json.JSONPropertyName;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Base64;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageModel {

    @NotNull
    private long id;

    @NotNull
    private String image;
}
