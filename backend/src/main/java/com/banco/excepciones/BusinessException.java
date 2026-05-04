package com.banco.excepciones;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
