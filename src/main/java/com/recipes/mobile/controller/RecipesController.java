package com.recipes.mobile.controller;
import com.recipes.mobile.DTOs.IngredientsDto;
import com.recipes.mobile.DTOs.RecipesDto;
import com.recipes.mobile.DTOs.reqRecipeDto;
import com.recipes.mobile.models.FoodDto;
import com.recipes.mobile.models.Recipe;
import com.recipes.mobile.services.RecipesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/recipe")
public class RecipesController {
    @Autowired
    RecipesServices recipesServices;

    @GetMapping("/get")
    public List<RecipesDto> getRecipes(){
        return recipesServices.getRecipes();
    }
    @PostMapping("/post")
    public ResponseEntity<String> postRecipes(@RequestBody reqRecipeDto reqRecipeDto){
        return  recipesServices.post(reqRecipeDto);
    }
    @PostMapping("/food")
    public ResponseEntity<List<FoodDto>> getFoods(){
        List<FoodDto> foods= recipesServices.Food();

       return new ResponseEntity<>(foods, HttpStatus.OK);
    }
    @PostMapping("/posibleFood")
    public ResponseEntity<List<Recipe>> getPosibleRecipes(@RequestBody List<FoodDto> foodsDto){
        List<Recipe> recipes = recipesServices.getPosibleRecipes(foodsDto);
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }
}
