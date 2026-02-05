package com.lixlim.rest_api.memo;

import com.lixlim.rest_api.memo.dto.MemoCreateRequest;
import com.lixlim.rest_api.memo.dto.MemoResponse;
import com.lixlim.rest_api.memo.dto.MemoUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/memo")
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memoService;

    @GetMapping
    public ResponseEntity<List<MemoResponse>> getMemos() {
        return ResponseEntity.ok(memoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemoResponse> getMemo(@PathVariable Long id) {
        return ResponseEntity.ok(memoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<MemoResponse> createMemo(@Valid @RequestBody MemoCreateRequest req) {
        var created = memoService.create(req);
        URI location = URI.create("/memo/" + created.id());
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemoResponse> updateMemo(
            @PathVariable Long id,
            @Valid @RequestBody MemoUpdateRequest req
    ) {
        return ResponseEntity.ok(memoService.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMemo(@PathVariable Long id) {
        memoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
