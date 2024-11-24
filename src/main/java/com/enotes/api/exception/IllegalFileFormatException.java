package com.enotes.api.exception;

public class IllegalFileFormatException extends RuntimeException{

    public IllegalFileFormatException(String message){
        super(message);
    }
}
