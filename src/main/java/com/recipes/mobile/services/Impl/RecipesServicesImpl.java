package com.recipes.mobile.services.Impl;

import com.recipes.mobile.DTOs.IngredientsDto;
import com.recipes.mobile.DTOs.RecipesDto;
import com.recipes.mobile.DTOs.reqRecipeDto;
import com.recipes.mobile.models.Ingredient;
import com.recipes.mobile.models.Recipe;
import com.recipes.mobile.repository.FoodRepository;
import com.recipes.mobile.repository.IngredientsRepository;
import com.recipes.mobile.repository.RecipesRepository;
import com.recipes.mobile.services.RecipesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipesServicesImpl implements RecipesServices {

    @Autowired
    private RecipesRepository recipesRepository;

    @Autowired
    private IngredientsRepository ingredientsRepository;
    @Autowired
    private FoodRepository foodRepository;

    @Override
    public List<RecipesDto>  getRecipes() {
        List<RecipesDto> recipesDtos = new ArrayList<>();

      List<Recipe> recipes = recipesRepository.findAll();

        for(Recipe recie:recipes){
            RecipesDto recipesDto = new RecipesDto();
            recipesDto.setId(recie.getId());
            List<IngredientsDto> ingredientsDtos = new ArrayList<>();
            List<Ingredient> Ingredient = ingredientsRepository.findAllByIdRecipe(recie.getId());

            recipesDto.setName(recie.getName());
            recipesDto.setDescription(recie.getDescription());
            for (Ingredient ingredient: Ingredient){
                IngredientsDto ingredientsDto = new IngredientsDto();

                ingredientsDto.setId(ingredient.getId());
                ingredientsDto.setFood(ingredient.getFood());
                ingredientsDto.setAmount(ingredient.getAmount());
                ingredientsDtos.add(ingredientsDto);
            }
            recipesDto.setIngredientsDto(ingredientsDtos);
            recipesDtos.add(recipesDto);
        }
        return recipesDtos;
    }

    @Override
    public ResponseEntity<String> post(reqRecipeDto reqRecipeDto) {
        Recipe recipe = new Recipe();
        Long idRecipe =  System.currentTimeMillis();

        recipe.setId(idRecipe);
        recipe.setName(reqRecipeDto.getName());
        recipe.setDescription(reqRecipeDto.getDescription());
        recipesRepository.save(recipe);
        for (IngredientsDto ingredit:reqRecipeDto.getIngredientsDto()){
            Ingredient ingredient = new Ingredient();
            ingredient.setFood(ingredit.getFood());
            ingredient.setAmount(ingredit.getAmount());
            ingredient.setIdRecipe(idRecipe);
            ingredientsRepository.save(ingredient);
        }

        return new ResponseEntity<>("success", HttpStatus.OK) ;
    }
}
