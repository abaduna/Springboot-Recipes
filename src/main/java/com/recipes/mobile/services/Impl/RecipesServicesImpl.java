package com.recipes.mobile.services.Impl;

import com.recipes.mobile.DTOs.IngredientsDto;
import com.recipes.mobile.DTOs.RecipesDto;
import com.recipes.mobile.DTOs.reqRecipeDto;
import com.recipes.mobile.models.FoodDto;
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

import java.util.*;
import java.util.stream.Collectors;

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

    @Override
    public List<FoodDto> Food() {
        List<FoodDto> foods = new ArrayList<>();
        Set<String> uniqueFoods = new HashSet<>(); // Para mantener un registro de combinaciones únicas de "name" y "amount"
        List<Ingredient> ingredients = ingredientsRepository.findAll();

        for (Ingredient ingredient : ingredients) {
            String uniqueKey = ingredient.getFood() + ":" + ingredient.getAmount(); // Genera una clave única

            // Solo agrega si la clave única no está en el conjunto
            if (!uniqueFoods.contains(uniqueKey)) {
                FoodDto foodDto = new FoodDto();
                foodDto.setNane(ingredient.getFood());
                foodDto.setAmount(ingredient.getAmount());

                foods.add(foodDto);
                uniqueFoods.add(uniqueKey); // Añade la clave al conjunto para futuras verificaciones
            }
        }

        return foods;
    }

    @Override
    public List<Recipe> getPosibleRecipes(List<FoodDto> foods) {
        List<Recipe> possibleRecipes = new ArrayList<>();
        List<Recipe> allRecipes = recipesRepository.findAll();

        // Crea un mapa de ingredientes disponibles para buscar de manera rápida
        Map<String, String> availableIngredients = foods.stream()
                .collect(Collectors.toMap(FoodDto::getNane, FoodDto::getAmount));

        boolean isFirstRecipe = true; // Inicializa una bandera para la primera receta

        for (Recipe recipe : allRecipes) {
            List<Ingredient> requiredIngredients = ingredientsRepository.findAllByIdRecipe(recipe.getId());

            boolean canMakeRecipe = true;

            for (Ingredient ingredient : requiredIngredients) {
                String requiredFood = ingredient.getFood();
                String requiredAmount = ingredient.getAmount();

                // Comprueba si el ingrediente está disponible y si la cantidad es suficiente
                if (!availableIngredients.containsKey(requiredFood) ||
                        !isAmountSufficient(availableIngredients.get(requiredFood), requiredAmount)) {
                    canMakeRecipe = false;
                    break; // Sale del bucle si no puede hacer la receta
                }
            }

            // Omitir la primera receta incluso si se puede hacer
            if (canMakeRecipe) {
                if (isFirstRecipe) {
                    isFirstRecipe = false; // Cambia la bandera después de la primera receta
                    continue; // Omitir esta iteración y pasar a la siguiente receta
                }
                possibleRecipes.add(recipe); // Agregar receta si no es la primera
            }
        }

        return possibleRecipes;
    }

    private boolean isAmountSufficient(String availableAmount, String requiredAmount) {
        try {
            double available = Double.parseDouble(availableAmount);
            double required = Double.parseDouble(requiredAmount);
            return available >= required;
        } catch (NumberFormatException e) {
            return false; // Si no se pueden convertir a números, retorna falso
        }
    }
}
