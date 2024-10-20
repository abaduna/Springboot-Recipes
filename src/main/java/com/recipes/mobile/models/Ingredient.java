package com.recipes.mobile.models;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name ="ingredients")
@Data
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private String Food;
    private Long idRecipe;
    private String amount;
}
