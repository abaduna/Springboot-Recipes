package com.recipes.mobile.repository;

import com.recipes.mobile.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipesRepository extends JpaRepository<Recipe,Long> {
}
