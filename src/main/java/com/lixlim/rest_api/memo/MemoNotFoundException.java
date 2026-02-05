package com.lixlim.rest_api.memo;

public class MemoNotFoundException extends RuntimeException {

    public MemoNotFoundException(Long id) {
        super("Memo not found: " + id);
    }
}
