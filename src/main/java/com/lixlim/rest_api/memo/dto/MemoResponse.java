package com.lixlim.rest_api.memo.dto;

import com.lixlim.rest_api.memo.entity.Memo;

public record MemoResponse(Long id, String title, String content) {

    public static MemoResponse fromEntity(Memo entity) {
        return new MemoResponse(entity.getId(), entity.getTitle(), entity.getContent());
    }
}
