package com.book.cook.service.mapper;

import com.book.cook.model.Recipe;
import com.book.cook.model.dto.RecipeDtoRequest;
import com.book.cook.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RecipeMapper {
    private final RecipeService recipeService;

    @Autowired
    public RecipeMapper(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    public Recipe mapFromDto(RecipeDtoRequest request) {
        Recipe recipe = new Recipe();
        recipe.setDish(request.getDish());
        recipe.setDescription(request.getDescription());
        recipe.setIngredients(request.getIngredients());
        return recipe;
    }
}
