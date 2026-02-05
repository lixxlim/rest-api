package com.lixlim.rest_api.memo;

import com.lixlim.rest_api.memo.dto.MemoCreateRequest;
import com.lixlim.rest_api.memo.dto.MemoResponse;
import com.lixlim.rest_api.memo.dto.MemoUpdateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemoController.class)
class MemoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    MemoService memoService;

    @Test
    void createMemo() throws Exception {
        var req = MemoCreateRequest.builder()
                .title("title")
                .content("content")
                .build();
        var res = new MemoResponse(1L, "title", "content");

        given(memoService.create(any(MemoCreateRequest.class))).willReturn(res);

        mockMvc.perform(
                        post("/memo")
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(req))
                )
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/memo/1"))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.content").value("content"));

        verify(memoService).create(any(MemoCreateRequest.class));
    }

    @Test
    void createMemoValidationFailure() throws Exception {
        var req = MemoCreateRequest.builder()
                .title("")
                .content("")
                .build();

        mockMvc.perform(
                        post("/memo")
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(req))
                )
                .andExpect(status().isBadRequest());

        verifyNoInteractions(memoService);
    }

    @Test
    void getMemo() throws Exception {
        var res = new MemoResponse(1L, "title", "content");

        given(memoService.findById(1L)).willReturn(res);

        mockMvc.perform(get("/memo/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.content").value("content"));

        verify(memoService).findById(1L);
    }

    @Test
    void listMemos() throws Exception {
        var res = List.of(new MemoResponse(1L, "title", "content"));

        given(memoService.findAll()).willReturn(res);

        mockMvc.perform(get("/memo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("title"))
                .andExpect(jsonPath("$[0].content").value("content"));

        verify(memoService).findAll();
    }

    @Test
    void updateMemo() throws Exception {
        var req = MemoUpdateRequest.builder()
                .title("new-title")
                .content("new-content")
                .build();
        var res = new MemoResponse(1L, "new-title", "new-content");

        given(memoService.update(any(Long.class), any(MemoUpdateRequest.class))).willReturn(res);

        mockMvc.perform(
                        put("/memo/1")
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(req))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("new-title"))
                .andExpect(jsonPath("$.content").value("new-content"));

        verify(memoService).update(any(Long.class), any(MemoUpdateRequest.class));
    }

    @Test
    void updateMemoValidationFailure() throws Exception {
        var req = MemoUpdateRequest.builder()
                .title("")
                .content("")
                .build();

        mockMvc.perform(
                        put("/memo/1")
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(req))
                )
                .andExpect(status().isBadRequest());

        verifyNoInteractions(memoService);
    }

    @Test
    void updateMemoNotFound() throws Exception {
        var req = MemoUpdateRequest.builder()
                .title("new-title")
                .content("new-content")
                .build();

        given(memoService.update(any(Long.class), any(MemoUpdateRequest.class)))
                .willThrow(new MemoNotFoundException(1L));

        mockMvc.perform(
                        put("/memo/1")
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(req))
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteMemo() throws Exception {
        willDoNothing().given(memoService).delete(1L);

        mockMvc.perform(delete("/memo/1"))
                .andExpect(status().isNoContent());

        verify(memoService).delete(1L);
    }

    @Test
    void deleteMemoNotFound() throws Exception {
        willThrow(new MemoNotFoundException(1L)).given(memoService).delete(1L);

        mockMvc.perform(delete("/memo/1"))
                .andExpect(status().isNotFound());
    }
}
