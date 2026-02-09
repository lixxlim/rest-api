package com.lixlim.rest_api.recipe;

import tools.jackson.databind.ObjectMapper;
import com.lixlim.rest_api.recipe.dto.RecipeCreateRequest;
import com.lixlim.rest_api.recipe.dto.RecipeUpdateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RecipeIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void recipeCrudFlow() throws Exception {
        var createReq = RecipeCreateRequest.builder()
                .title("title")
                .description("description")
                .ingredients(List.of("salt", "pepper"))
                .steps(List.of("mix", "cook"))
                .servings(2)
                .cookTimeMinutes(10)
                .tags(List.of("quick"))
                .build();

        MvcResult createResult = mockMvc.perform(
                        post("/recipes")
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createReq))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.description").value("description"))
                .andReturn();

        long id = objectMapper.readTree(createResult.getResponse().getContentAsString())
                .get("id").asLong();

        mockMvc.perform(get("/recipes/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.ingredients[0]").value("salt"));

        mockMvc.perform(get("/recipes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(id));

        var updateReq = RecipeUpdateRequest.builder()
                .title("new-title")
                .description("new-description")
                .ingredients(List.of("sugar"))
                .steps(List.of("bake"))
                .servings(3)
                .cookTimeMinutes(30)
                .tags(List.of("dessert"))
                .build();

        mockMvc.perform(
                        put("/recipes/{id}", id)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateReq))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.title").value("new-title"))
                .andExpect(jsonPath("$.steps[0]").value("bake"));

        mockMvc.perform(delete("/recipes/{id}", id))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/recipes/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void createRecipeValidationFail() throws Exception {
        var createReq = RecipeCreateRequest.builder()
                .title("")
                .description("")
                .ingredients(List.of())
                .steps(List.of())
                .servings(0)
                .cookTimeMinutes(0)
                .tags(List.of())
                .build();

        mockMvc.perform(
                        post("/recipes")
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createReq))
                )
                .andExpect(status().isBadRequest());
    }
}
