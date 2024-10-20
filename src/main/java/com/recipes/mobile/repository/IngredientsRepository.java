package com.recipes.mobile.repository;

import com.recipes.mobile.models.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientsRepository  extends JpaRepository<Ingredient,Long> {
    List<Ingredient> findAllByIdRecipe(Long id);
}
