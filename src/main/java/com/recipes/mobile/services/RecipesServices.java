package com.recipes.mobile.services;

import com.recipes.mobile.DTOs.RecipesDto;
import com.recipes.mobile.DTOs.reqRecipeDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RecipesServices {
    List<RecipesDto> getRecipes();
    ResponseEntity<String> post(reqRecipeDto reqRecipeDto);
}
