package com.lixlim.rest_api.memo.dto;

import com.lixlim.rest_api.memo.entity.Memo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemoCreateResponse {

    private Long id;

    public static MemoCreateResponse fromEntity(Memo entity) {
        return MemoCreateResponse.builder()
                .id(entity.getId())
                .build();
    }
}
