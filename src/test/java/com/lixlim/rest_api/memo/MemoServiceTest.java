package com.lixlim.rest_api.memo;

import com.lixlim.rest_api.memo.dto.MemoCreateRequest;
import com.lixlim.rest_api.memo.dto.MemoUpdateRequest;
import com.lixlim.rest_api.memo.entity.Memo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MemoServiceTest {

    @Mock
    MemoRepository memoRepository;

    @InjectMocks
    MemoServiceImpl memoService;

    @Test
    void createMemo() {
        var req = MemoCreateRequest.builder()
                .title("title")
                .content("content")
                .build();
        var saved = Memo.builder()
                .title("title")
                .content("content")
                .build();
        ReflectionTestUtils.setField(saved, "id", 1L);

        given(memoRepository.save(any(Memo.class))).willReturn(saved);

        var res = memoService.create(req);

        then(res.id()).isEqualTo(1L);
        then(res.title()).isEqualTo("title");
        then(res.content()).isEqualTo("content");
    }

    @Test
    void findMemoById() {
        var memo = Memo.builder()
                .title("title")
                .content("content")
                .build();
        ReflectionTestUtils.setField(memo, "id", 1L);

        given(memoRepository.findById(1L)).willReturn(Optional.of(memo));

        var res = memoService.findById(1L);

        then(res.id()).isEqualTo(1L);
        then(res.title()).isEqualTo("title");
        then(res.content()).isEqualTo("content");
    }

    @Test
    void findAllMemos() {
        var memo = Memo.builder()
                .title("title")
                .content("content")
                .build();
        ReflectionTestUtils.setField(memo, "id", 1L);

        given(memoRepository.findAll()).willReturn(List.of(memo));

        var res = memoService.findAll();

        then(res).hasSize(1);
        then(res.getFirst().id()).isEqualTo(1L);
    }

    @Test
    void updateMemo() {
        var memo = Memo.builder()
                .title("title")
                .content("content")
                .build();
        ReflectionTestUtils.setField(memo, "id", 1L);
        var req = MemoUpdateRequest.builder()
                .title("new-title")
                .content("new-content")
                .build();

        given(memoRepository.findById(1L)).willReturn(Optional.of(memo));
        var res = memoService.update(1L, req);

        then(res.id()).isEqualTo(1L);
        then(res.title()).isEqualTo("new-title");
        then(res.content()).isEqualTo("new-content");
    }

    @Test
    void deleteMemo() {
        var memo = Memo.builder()
                .title("title")
                .content("content")
                .build();
        ReflectionTestUtils.setField(memo, "id", 1L);

        given(memoRepository.findById(1L)).willReturn(Optional.of(memo));

        memoService.delete(1L);

        verify(memoRepository).delete(memo);
    }

    @Test
    void findMemoByIdNotFound() {
        given(memoRepository.findById(1L)).willReturn(Optional.empty());

        thenThrownBy(() -> memoService.findById(1L))
                .isInstanceOf(MemoNotFoundException.class);
    }

    @Test
    void updateMemoNotFound() {
        var req = MemoUpdateRequest.builder()
                .title("new-title")
                .content("new-content")
                .build();

        given(memoRepository.findById(1L)).willReturn(Optional.empty());

        thenThrownBy(() -> memoService.update(1L, req))
                .isInstanceOf(MemoNotFoundException.class);
    }

    @Test
    void deleteMemoNotFound() {
        given(memoRepository.findById(1L)).willReturn(Optional.empty());

        thenThrownBy(() -> memoService.delete(1L))
                .isInstanceOf(MemoNotFoundException.class);
    }
}
