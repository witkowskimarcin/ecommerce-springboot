package com.example.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import org.hibernate.annotations.NaturalId;

@Data
public class UserModel{

    @NotNull
    private Long id;

    @NotBlank
    @Size(min=3, max = 50)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(min=6, max = 100)
    private String password;

    private Set<RoleModel> roles = new HashSet<>();

    @Size(min=3, max = 50)
    private String firstname;

    @Size(min=3, max = 50)
    private String lastname;

    @Size(max = 30)
    private String locality;

    @Size(max = 30)
    private String street;

    @Size(max = 6)
    private String zipCode;

    @Size(max = 20)
    private String phone;

    @NotNull
    private int active;
}
