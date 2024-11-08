package com.enotes.api.exception;

public class InvalidArgumentException extends RuntimeException{

    InvalidArgumentException(String message){
        super(message);
    }
}
