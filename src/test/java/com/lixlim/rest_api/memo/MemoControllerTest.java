package com.lixlim.rest_api.memo;

import com.lixlim.rest_api.memo.dto.MemoCreateRequest;
import com.lixlim.rest_api.memo.dto.MemoCreateResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemoController.class)
public class MemoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    MemoService memoService;

    @Test
    void memoControllerTest() throws Exception {
        // test params
        var request = MemoCreateRequest.builder()
                .title("title")
                .content("content")
                .build();
        var response = MemoCreateResponse.builder()
                .id(1L)
                .build();

        // given
        given(memoService.create(any(MemoCreateRequest.class))).willReturn(response);

        // when
        var result = mockMvc.perform(
                post("/memos").contentType(APPLICATION_JSON)
                              .content(objectMapper.writeValueAsString(request))
        );

        // then
        result.andExpect(status().isOk())
              .andExpect(jsonPath("$.id").value(1L));
    }
}
