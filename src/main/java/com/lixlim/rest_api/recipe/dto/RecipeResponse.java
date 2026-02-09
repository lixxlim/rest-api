package com.lixlim.rest_api.recipe.dto;

import com.lixlim.rest_api.recipe.entity.Recipe;

import java.util.List;

public record RecipeResponse(
        Long id,
        String title,
        String description,
        List<String> ingredients,
        List<String> steps,
        int servings,
        int cookTimeMinutes,
        List<String> tags
) {

    public static RecipeResponse fromEntity(Recipe entity) {
        return new RecipeResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getIngredients(),
                entity.getSteps(),
                entity.getServings(),
                entity.getCookTimeMinutes(),
                entity.getTags()
        );
    }
}
