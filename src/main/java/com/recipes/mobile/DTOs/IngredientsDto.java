package com.recipes.mobile.DTOs;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


@Data
public class IngredientsDto {
    @Id

    private  Long id;

    private String food;
    private String amount;
}
