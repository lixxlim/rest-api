package com.lixlim.rest_api.memo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record MemoUpdateRequest(
        @NotBlank String title,
        @NotBlank String content
) {
}
