package com.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.entity.RoleName;
import lombok.Data;
import org.hibernate.annotations.NaturalId;

@Data
public class RoleModel {

    @NotNull
    private Long id;

    @NotNull
    private RoleName name;
}
