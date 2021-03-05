package com.book.cook.controller;

import com.book.cook.model.Recipe;
import com.book.cook.model.dto.RecipeDtoRequest;
import com.book.cook.service.RecipeService;
import com.book.cook.service.mapper.RecipeMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recipes")
public class RecipeController {
    private final RecipeService recipeService;
    private final RecipeMapper recipeMapper;

    @Autowired
    public RecipeController(RecipeService recipeService, RecipeMapper recipeMapper) {
        this.recipeService = recipeService;
        this.recipeMapper = recipeMapper;
    }

    @GetMapping
    public List<Recipe> getAllRecipesAsc() {
        return recipeService.getAllRecipes();
    }

    @PostMapping("/add")
    public void addRecipe(@RequestBody RecipeDtoRequest request) {
        Recipe recipe = recipeMapper.mapFromDto(request);
        recipeService.add(recipe);
    }

    @PutMapping("/{dish}")
    public void updateRecipe(@PathVariable String dish, @RequestBody RecipeDtoRequest request) {
        Recipe recipe = recipeMapper.mapFromDto(request);
        recipe.setId(recipeService.getRecipeByDish(dish).getId());
        recipeService.update(recipe);
    }

    @GetMapping("/{dish}")
    public Recipe getRecipeByDish(@PathVariable String dish) {
        return recipeService.getRecipeByDish(dish);
    }

    @DeleteMapping("/{dish}")
    public void delete(@PathVariable String dish) {
        recipeService.deleteRecipeByDish(dish);
    }

    @GetMapping("/versions/{dish}")
    public List<Recipe> getRecipeVersionsByDish(@PathVariable String dish) {
        return recipeService.showRecipeVersionsByDish(dish);
    }

    @PutMapping("/fork/{dish}")
    public void addChildRecipe(@PathVariable String dish, @RequestBody RecipeDtoRequest request) {
        Recipe recipe = recipeMapper.mapFromDto(request);
        recipeService.setChildRecipe(recipe, recipeService.getRecipeByDish(dish));
    }
}
