package com.lixlim.rest_api.recipe;

public class RecipeNotFoundException extends RuntimeException {

    public RecipeNotFoundException(Long id) {
        super("Recipe not found: " + id);
    }
}
