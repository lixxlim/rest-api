package com.lixlim.rest_api.memo.dto;

import com.lixlim.rest_api.memo.entity.Memo;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record MemoCreateRequest(
        @NotBlank String title,
        @NotBlank String content
) {

    public Memo toEntity() {
        return Memo.builder()
                .title(this.title)
                .content(this.content)
                .build();
    }
}
