package com.company.gamestore.exception;

public class UnknownStateCodeException extends RuntimeException {
    public UnknownStateCodeException(String message) {
        super(message);
    }
}