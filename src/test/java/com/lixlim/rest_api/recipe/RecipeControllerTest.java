package com.lixlim.rest_api.recipe;

import tools.jackson.databind.ObjectMapper;
import com.lixlim.rest_api.recipe.dto.RecipeCreateRequest;
import com.lixlim.rest_api.recipe.dto.RecipeResponse;
import com.lixlim.rest_api.recipe.dto.RecipeUpdateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecipeController.class)
class RecipeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    RecipeService recipeService;

    @Test
    void createRecipe() throws Exception {
        var req = RecipeCreateRequest.builder()
                .title("title")
                .description("description")
                .ingredients(List.of("salt"))
                .steps(List.of("mix"))
                .servings(1)
                .cookTimeMinutes(5)
                .tags(List.of("quick"))
                .build();

        var res = new RecipeResponse(
                1L,
                "title",
                "description",
                List.of("salt"),
                List.of("mix"),
                1,
                5,
                List.of("quick")
        );

        given(recipeService.create(any(RecipeCreateRequest.class))).willReturn(res);

        mockMvc.perform(
                        post("/recipes")
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(req))
                )
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/recipes/1"))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.description").value("description"));

        verify(recipeService).create(any(RecipeCreateRequest.class));
    }

    @Test
    void getRecipe() throws Exception {
        var res = new RecipeResponse(
                1L,
                "title",
                "description",
                List.of("salt"),
                List.of("mix"),
                1,
                5,
                List.of("quick")
        );

        given(recipeService.findById(1L)).willReturn(res);

        mockMvc.perform(get("/recipes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.description").value("description"));

        verify(recipeService).findById(1L);
    }

    @Test
    void listRecipes() throws Exception {
        var res = new RecipeResponse(
                1L,
                "title",
                "description",
                List.of("salt"),
                List.of("mix"),
                1,
                5,
                List.of("quick")
        );

        given(recipeService.findAll(any(Pageable.class)))
                .willReturn(new PageImpl<>(List.of(res), PageRequest.of(0, 20), 1));

        mockMvc.perform(get("/recipes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[0].title").value("title"));

        verify(recipeService).findAll(any(Pageable.class));
    }

    @Test
    void updateRecipe() throws Exception {
        var req = RecipeUpdateRequest.builder()
                .title("new-title")
                .description("new-description")
                .ingredients(List.of("sugar"))
                .steps(List.of("bake"))
                .servings(3)
                .cookTimeMinutes(30)
                .tags(List.of("dessert"))
                .build();

        var res = new RecipeResponse(
                1L,
                "new-title",
                "new-description",
                List.of("sugar"),
                List.of("bake"),
                3,
                30,
                List.of("dessert")
        );

        given(recipeService.update(any(Long.class), any(RecipeUpdateRequest.class))).willReturn(res);

        mockMvc.perform(
                        put("/recipes/1")
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(req))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("new-title"));

        verify(recipeService).update(any(Long.class), any(RecipeUpdateRequest.class));
    }

    @Test
    void deleteRecipe() throws Exception {
        willDoNothing().given(recipeService).delete(1L);

        mockMvc.perform(delete("/recipes/1"))
                .andExpect(status().isNoContent());

        verify(recipeService).delete(1L);
    }
}
