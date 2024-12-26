package com.example.ch4_1_newsfeed.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * 응답에 필요한 값들을 가지는 예외 클래스
 * AOP 클래스에서 이 예외를 던지도록 해 ControllerAdvice 에서 각 예외 사항들을 통일된 방식으로 처리할 수 있도록 함
 */
public class ResponseException extends RuntimeException {
    protected final HttpStatus status;
    protected final String message;

    public ResponseException(String message, HttpStatus status) {
        super(message);
        this.message = message;
        this.status = status;
    }

    public ResponseEntity<Map<String, String>> getResponseEntity() {
        Map<String, String> body = new HashMap<>();
        body.put("message", message);
        return ResponseEntity.status(this.status).body(body);
    }
}
