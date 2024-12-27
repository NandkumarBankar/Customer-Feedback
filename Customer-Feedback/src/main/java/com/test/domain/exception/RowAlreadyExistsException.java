package com.test.domain.exception;

public class RowAlreadyExistsException extends RuntimeException {
    public RowAlreadyExistsException(String message) {
        super(message);
    }
}

