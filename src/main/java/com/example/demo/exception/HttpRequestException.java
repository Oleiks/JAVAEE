package com.example.demo.exception;

import lombok.Getter;

@Getter
public class HttpRequestException extends RuntimeException {

    private final int responseCode;

    public HttpRequestException(String message, int responseCode) {
        super(message);
        this.responseCode = responseCode;
    }
}
