package com.book.cook.service.serviceimpl;

import com.book.cook.exception.RecipeProcessingException;
import com.book.cook.model.Recipe;
import com.book.cook.repository.RecipeRepository;
import com.book.cook.service.RecipeService;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final EntityManager entityManager;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository, EntityManager entityManager) {
        this.recipeRepository = recipeRepository;
        this.entityManager = entityManager;
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findByOrderByDish();
    }

    @Override
    public void add(Recipe recipe) {
        recipe.setDate(LocalDate.now());
        if (recipeRepository.findRecipeByDish(recipe.getDish()).isPresent()) {
            throw new RecipeProcessingException("Recipe \"" + recipe.getDish()
                    + "\" is already exist.");
        }
        recipeRepository.save(recipe);
    }

    @Override
    public void update(Recipe recipe) {
        recipe.setDate(LocalDate.now());
        recipeRepository.save(recipe);
    }

    @Override
    public Recipe getRecipeByDish(String dish) {
        return recipeRepository.findRecipeByDish(dish)
                .orElseThrow(() -> new RecipeProcessingException("Can`t find recipe by dish "
                        + dish));
    }

    @Override
    public void deleteRecipeByDish(String dish) {
        recipeRepository.deleteByDish(dish);
    }

    @Override
    public List showRecipeVersionsByDish(String dish) {
        AuditReader reader = AuditReaderFactory.get(entityManager);
        Optional<Recipe> recipe = recipeRepository.findRecipeByDish(dish);
        Long id = recipe.get().getId();
        AuditQuery query = reader.createQuery()
                .forRevisionsOfEntity(Recipe.class, true, false)
                .add(AuditEntity.id().eq(id));
        return query.getResultList();

    }

    @Override
    public void setChildRecipe(Recipe child, Recipe parent) {
        child.setParent(parent);
        child.setDate(LocalDate.now());
        recipeRepository.save(child);
    }
}
