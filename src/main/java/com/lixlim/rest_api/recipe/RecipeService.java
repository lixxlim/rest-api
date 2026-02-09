package com.lixlim.rest_api.recipe;

import com.lixlim.rest_api.recipe.dto.RecipeCreateRequest;
import com.lixlim.rest_api.recipe.dto.RecipeResponse;
import com.lixlim.rest_api.recipe.dto.RecipeUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecipeService {

    Page<RecipeResponse> findAll(Pageable pageable);

    RecipeResponse findById(Long id);

    RecipeResponse create(RecipeCreateRequest req);

    RecipeResponse update(Long id, RecipeUpdateRequest req);

    void delete(Long id);
}
