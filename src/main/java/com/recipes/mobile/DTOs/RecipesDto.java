package com.recipes.mobile.DTOs;


import lombok.Data;

import java.util.List;


@Data
public class RecipesDto {
    private  Long id;
    private String name;
    private List<IngredientsDto> ingredientsDto;
    private String description;

}
