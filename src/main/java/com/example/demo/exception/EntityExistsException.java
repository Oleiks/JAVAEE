package com.example.demo.exception;

public class EntityExistsException extends HttpRequestException {

    private static final int RESPONSE_CODE = 400;

    public EntityExistsException(String message) {
        super(message, RESPONSE_CODE);
    }
}
