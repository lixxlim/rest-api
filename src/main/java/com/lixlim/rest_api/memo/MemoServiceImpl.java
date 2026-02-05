package com.lixlim.rest_api.memo;

import com.lixlim.rest_api.memo.dto.MemoCreateRequest;
import com.lixlim.rest_api.memo.dto.MemoResponse;
import com.lixlim.rest_api.memo.dto.MemoUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemoServiceImpl implements MemoService {

    private final MemoRepository memoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MemoResponse> findAll() {
        return memoRepository.findAll().stream()
                .map(MemoResponse::fromEntity)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MemoResponse findById(Long id) {
        var memo = memoRepository.findById(id)
                .orElseThrow(() -> new MemoNotFoundException(id));
        return MemoResponse.fromEntity(memo);
    }

    @Override
    public MemoResponse create(MemoCreateRequest req) {
        var createdEntity = memoRepository.save(req.toEntity());
        return MemoResponse.fromEntity(createdEntity);
    }

    @Override
    public MemoResponse update(Long id, MemoUpdateRequest req) {
        var memo = memoRepository.findById(id)
                .orElseThrow(() -> new MemoNotFoundException(id));
        memo.update(req.title(), req.content());
        return MemoResponse.fromEntity(memo);
    }

    @Override
    public void delete(Long id) {
        var memo = memoRepository.findById(id)
                .orElseThrow(() -> new MemoNotFoundException(id));
        memoRepository.delete(memo);
    }
}
