package com.lixlim.rest_api.recipe.dto;

import com.lixlim.rest_api.recipe.entity.Recipe;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public record RecipeCreateRequest(
        @NotBlank String title,
        @NotBlank String description,
        @NotEmpty List<@NotBlank String> ingredients,
        @NotEmpty List<@NotBlank String> steps,
        @Min(1) int servings,
        @Min(1) int cookTimeMinutes,
        @Size(max = 50) List<@NotBlank String> tags
) {

    public Recipe toEntity() {
        return Recipe.builder()
                .title(this.title)
                .description(this.description)
                .ingredients(this.ingredients == null ? null : new ArrayList<>(this.ingredients))
                .steps(this.steps == null ? null : new ArrayList<>(this.steps))
                .servings(this.servings)
                .cookTimeMinutes(this.cookTimeMinutes)
                .tags(this.tags == null ? null : new ArrayList<>(this.tags))
                .build();
    }
}
