package com.andreina.ushi.exception;

public class UshiException extends Exception {

    public UshiException(String message) {
        super(message);
    }

    public UshiException(Throwable cause) {
        super(cause);
    }

    public UshiException(String message, Throwable cause) {
        super(message, cause);
    }
}