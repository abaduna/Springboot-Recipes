package com.recipes.mobile.services;

import com.recipes.mobile.DTOs.RecipesDto;
import com.recipes.mobile.DTOs.reqRecipeDto;
import com.recipes.mobile.models.FoodDto;
import com.recipes.mobile.models.Recipe;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RecipesServices {
    List<RecipesDto> getRecipes();
    ResponseEntity<String> post(reqRecipeDto reqRecipeDto);
    List<FoodDto> Food();
    List<Recipe> getPosibleRecipes(List<FoodDto> foods);
}
