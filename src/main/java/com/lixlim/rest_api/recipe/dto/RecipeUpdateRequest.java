package com.lixlim.rest_api.recipe.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;

@Builder
public record RecipeUpdateRequest(
        @NotBlank String title,
        @NotBlank String description,
        @NotEmpty List<@NotBlank String> ingredients,
        @NotEmpty List<@NotBlank String> steps,
        @Min(1) int servings,
        @Min(1) int cookTimeMinutes,
        @Size(max = 50) List<@NotBlank String> tags
) {
}
