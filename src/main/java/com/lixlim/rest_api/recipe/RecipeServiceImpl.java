package com.lixlim.rest_api.recipe;

import com.lixlim.rest_api.recipe.dto.RecipeCreateRequest;
import com.lixlim.rest_api.recipe.dto.RecipeResponse;
import com.lixlim.rest_api.recipe.dto.RecipeUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<RecipeResponse> findAll(Pageable pageable) {
        return recipeRepository.findAll(pageable)
                .map(RecipeResponse::fromEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public RecipeResponse findById(Long id) {
        var recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException(id));
        return RecipeResponse.fromEntity(recipe);
    }

    @Override
    public RecipeResponse create(RecipeCreateRequest req) {
        var created = recipeRepository.save(req.toEntity());
        return RecipeResponse.fromEntity(created);
    }

    @Override
    public RecipeResponse update(Long id, RecipeUpdateRequest req) {
        var recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException(id));

        recipe.update(
                req.title(),
                req.description(),
                req.ingredients(),
                req.steps(),
                req.servings(),
                req.cookTimeMinutes(),
                req.tags()
        );

        return RecipeResponse.fromEntity(recipe);
    }

    @Override
    public void delete(Long id) {
        var recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException(id));
        recipeRepository.delete(recipe);
    }
}
