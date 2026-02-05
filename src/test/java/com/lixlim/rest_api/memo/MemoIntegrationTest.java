package com.lixlim.rest_api.memo;

import com.lixlim.rest_api.memo.dto.MemoCreateRequest;
import com.lixlim.rest_api.memo.dto.MemoUpdateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.databind.ObjectMapper;

import static org.assertj.core.api.BDDAssertions.then;
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
class MemoIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void memoCrudFlow() throws Exception {
        var createReq = MemoCreateRequest.builder()
                .title("title")
                .content("content")
                .build();

        MvcResult createResult = mockMvc.perform(
                        post("/memo")
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createReq))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.content").value("content"))
                .andReturn();

        long id = objectMapper.readTree(createResult.getResponse().getContentAsString())
                .get("id").asLong();

        String location = createResult.getResponse().getHeader("Location");
        then(location).isEqualTo("/memo/" + id);

        mockMvc.perform(get("/memo/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.content").value("content"));

        mockMvc.perform(get("/memo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id));

        var updateReq = MemoUpdateRequest.builder()
                .title("new-title")
                .content("new-content")
                .build();

        mockMvc.perform(
                        put("/memo/{id}", id)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateReq))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.title").value("new-title"))
                .andExpect(jsonPath("$.content").value("new-content"));

        mockMvc.perform(delete("/memo/{id}", id))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/memo/{id}", id))
                .andExpect(status().isNotFound());
    }
}
