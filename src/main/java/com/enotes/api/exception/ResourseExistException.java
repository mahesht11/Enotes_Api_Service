package com.enotes.api.exception;

public class ResourseExistException extends RuntimeException{

    public ResourseExistException(String message){
        super(message);
    }
}
