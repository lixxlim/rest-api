package com.lixlim.rest_api.recipe;

import com.lixlim.rest_api.recipe.dto.RecipeCreateRequest;
import com.lixlim.rest_api.recipe.dto.RecipeUpdateRequest;
import com.lixlim.rest_api.recipe.entity.Recipe;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @Mock
    RecipeRepository recipeRepository;

    @InjectMocks
    RecipeServiceImpl recipeService;

    @Test
    void createRecipe() {
        var req = RecipeCreateRequest.builder()
                .title("title")
                .description("description")
                .ingredients(List.of("salt", "pepper"))
                .steps(List.of("mix", "cook"))
                .servings(2)
                .cookTimeMinutes(10)
                .tags(List.of("quick"))
                .build();

        var saved = Recipe.builder()
                .title("title")
                .description("description")
                .ingredients(List.of("salt", "pepper"))
                .steps(List.of("mix", "cook"))
                .servings(2)
                .cookTimeMinutes(10)
                .tags(List.of("quick"))
                .build();
        ReflectionTestUtils.setField(saved, "id", 1L);

        given(recipeRepository.save(any(Recipe.class))).willReturn(saved);

        var res = recipeService.create(req);

        then(res.id()).isEqualTo(1L);
        then(res.title()).isEqualTo("title");
        then(res.ingredients()).hasSize(2);
        then(res.steps()).hasSize(2);
    }

    @Test
    void findRecipeById() {
        var recipe = Recipe.builder()
                .title("title")
                .description("description")
                .ingredients(List.of("salt"))
                .steps(List.of("mix"))
                .servings(1)
                .cookTimeMinutes(5)
                .tags(List.of())
                .build();
        ReflectionTestUtils.setField(recipe, "id", 1L);

        given(recipeRepository.findById(1L)).willReturn(Optional.of(recipe));

        var res = recipeService.findById(1L);

        then(res.id()).isEqualTo(1L);
        then(res.title()).isEqualTo("title");
        then(res.ingredients()).containsExactly("salt");
    }

    @Test
    void findAllRecipes() {
        var recipe = Recipe.builder()
                .title("title")
                .description("description")
                .ingredients(List.of("salt"))
                .steps(List.of("mix"))
                .servings(1)
                .cookTimeMinutes(5)
                .tags(List.of())
                .build();
        ReflectionTestUtils.setField(recipe, "id", 1L);

        given(recipeRepository.findAll(any(Pageable.class)))
                .willReturn(new PageImpl<>(List.of(recipe)));

        var res = recipeService.findAll(PageRequest.of(0, 20));

        then(res.getTotalElements()).isEqualTo(1);
        then(res.getContent().getFirst().id()).isEqualTo(1L);
    }

    @Test
    void updateRecipe() {
        var recipe = Recipe.builder()
                .title("title")
                .description("description")
                .ingredients(List.of("salt"))
                .steps(List.of("mix"))
                .servings(1)
                .cookTimeMinutes(5)
                .tags(List.of())
                .build();
        ReflectionTestUtils.setField(recipe, "id", 1L);

        var req = RecipeUpdateRequest.builder()
                .title("new-title")
                .description("new-description")
                .ingredients(List.of("sugar"))
                .steps(List.of("bake"))
                .servings(3)
                .cookTimeMinutes(30)
                .tags(List.of("dessert"))
                .build();

        given(recipeRepository.findById(1L)).willReturn(Optional.of(recipe));

        var res = recipeService.update(1L, req);

        then(res.title()).isEqualTo("new-title");
        then(res.description()).isEqualTo("new-description");
        then(res.ingredients()).containsExactly("sugar");
        then(res.steps()).containsExactly("bake");
        then(res.tags()).containsExactly("dessert");
    }

    @Test
    void deleteRecipe() {
        var recipe = Recipe.builder()
                .title("title")
                .description("description")
                .ingredients(List.of("salt"))
                .steps(List.of("mix"))
                .servings(1)
                .cookTimeMinutes(5)
                .tags(List.of())
                .build();
        ReflectionTestUtils.setField(recipe, "id", 1L);

        given(recipeRepository.findById(1L)).willReturn(Optional.of(recipe));

        recipeService.delete(1L);

        verify(recipeRepository).delete(recipe);
    }

    @Test
    void findRecipeByIdNotFound() {
        given(recipeRepository.findById(1L)).willReturn(Optional.empty());

        thenThrownBy(() -> recipeService.findById(1L))
                .isInstanceOf(RecipeNotFoundException.class);
    }
}
