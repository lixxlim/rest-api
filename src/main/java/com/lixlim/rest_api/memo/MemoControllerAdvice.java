package com.lixlim.rest_api.memo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MemoControllerAdvice {

    @ExceptionHandler(MemoNotFoundException.class)
    public ResponseEntity<Void> handleMemoNotFound(MemoNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }
}
