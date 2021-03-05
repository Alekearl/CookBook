package com.book.cook.service;

import com.book.cook.model.Recipe;
import java.util.List;

public interface RecipeService {
    List<Recipe> getAllRecipes();

    void add(Recipe recipe);

    void update(Recipe recipe);

    Recipe getRecipeByDish(String dish);

    void deleteRecipeByDish(String dish);

    List showRecipeVersionsByDish(String dish);

    void setChildRecipe(Recipe child, Recipe parent);
}
