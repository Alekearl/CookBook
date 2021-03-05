package com.book.cook.repository;

import com.book.cook.model.Recipe;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByOrderByDish();

    Optional<Recipe> findRecipeByDish(String dish);

    void deleteByDish(String dish);
}
