package com.lixlim.rest_api.memo;

import com.lixlim.rest_api.memo.dto.MemoCreateRequest;
import com.lixlim.rest_api.memo.dto.MemoResponse;
import com.lixlim.rest_api.memo.dto.MemoUpdateRequest;

import java.util.List;

public interface MemoService {

    List<MemoResponse> findAll();

    MemoResponse findById(Long id);

    MemoResponse create(MemoCreateRequest req);

    MemoResponse update(Long id, MemoUpdateRequest req);

    void delete(Long id);
}
