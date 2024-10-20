package com.recipes.mobile.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name ="recipes")
@Data
public class Recipe {
    @Id
    private  Long  id;

    private String name;
    private String description;

}
