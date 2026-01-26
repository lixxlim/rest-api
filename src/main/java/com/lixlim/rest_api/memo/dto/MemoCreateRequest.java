package com.lixlim.rest_api.memo.dto;

import com.lixlim.rest_api.memo.entity.Memo;
import lombok.Builder;

@Builder
public record MemoCreateRequest(String title, String content) {

    public Memo toEntity() {
        return Memo.builder()
                .title(this.title)
                .content(this.content)
                .build();
    }
}
