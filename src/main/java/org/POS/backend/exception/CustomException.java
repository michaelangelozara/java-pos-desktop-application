package org.POS.backend.exception;

public class CustomException extends RuntimeException{
    public CustomException(String message, Throwable throwable){
        super(message, throwable);
    }
}
