package com.recipes.mobile.repository;

import com.recipes.mobile.models.Foods;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Foods,Long> {
}
