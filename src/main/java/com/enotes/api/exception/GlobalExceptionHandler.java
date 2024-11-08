package com.enotes.api.exception;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@Builder
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleException(Exception e, WebRequest request){
        log.error("Global Exception handler : handle Exception :: " + e.getMessage());
        ErrorDetails errorDetails = ErrorDetails.builder()
                .timestamp(new Date())
                .message(e.getMessage())
                .details(request.getDescription(false))
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException e, WebRequest request){
       log.error("Global Exception handler : handle ResourceNotFoundException :: " + e.getMessage());
        ErrorDetails errorDetails = ErrorDetails.builder()
                .timestamp(new Date())
                .message(e.getMessage())
                .details(request.getDescription(false))
                .build();
       return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorDetails> handleNullPointerException(NullPointerException e, WebRequest request){
        log.error("Global Exception handler : handle NullPointerException :: " + e.getMessage());
        ErrorDetails errorDetails = ErrorDetails.builder()
                                        .timestamp(new Date())
                                        .message(e.getMessage())
                                        .details(request.getDescription(false))
                                        .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
