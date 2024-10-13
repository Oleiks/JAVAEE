package com.example.demo.exception;

public class EntityNotFoundException extends HttpRequestException {

    private static final int RESPONSE_CODE = 404;

    public EntityNotFoundException(String message) {
        super(message,RESPONSE_CODE);
    }
}
