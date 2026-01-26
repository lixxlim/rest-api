package com.lixlim.rest_api.memo;

import com.lixlim.rest_api.memo.dto.MemoCreateRequest;
import com.lixlim.rest_api.memo.dto.MemoCreateResponse;

public interface MemoService {

    MemoCreateResponse create(MemoCreateRequest req);
}
