package com.lixlim.rest_api.recipe;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RecipeControllerAdvice {

    @ExceptionHandler(RecipeNotFoundException.class)
    public ResponseEntity<Void> handleRecipeNotFound(RecipeNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }
}
