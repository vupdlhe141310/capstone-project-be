package com.homesharing_backend.exception;


public class EmptyDataException extends RuntimeException {

    public EmptyDataException(String message){
        super(message);
    }
}
