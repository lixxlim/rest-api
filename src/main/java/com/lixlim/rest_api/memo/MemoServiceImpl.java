package com.lixlim.rest_api.memo;

import com.lixlim.rest_api.memo.dto.MemoCreateRequest;
import com.lixlim.rest_api.memo.dto.MemoCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemoServiceImpl implements MemoService {

    private final MemoRepository memoRepository;

    @Override
    public MemoCreateResponse create(MemoCreateRequest req) {
        var createdEntity = memoRepository.save(req.toEntity());
        return MemoCreateResponse.fromEntity(createdEntity);
    }
}
