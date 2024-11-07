package com.enotes.api.exception;

public class ResourceNotFoundException extends RuntimeException{
    public String message;
    public ResourceNotFoundException(String message){
        super();
        this.message = message;
    }
}
