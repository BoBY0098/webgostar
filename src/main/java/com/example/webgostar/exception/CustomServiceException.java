package com.example.webgostar.exception;

public class CustomServiceException extends RuntimeException{
    public CustomServiceException(String message) {
        super(message);
    }
}
