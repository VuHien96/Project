package com.vuhien.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(DuplicateRecordException.class)
    public ResponseEntity<?> handlerDuplicateException(DuplicateRecordException ex, WebRequest request) {
        //Log err
        ErrorResponse err = new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handlerNotFoundException(NotFoundException ex, WebRequest request) {
        //Log err
        ErrorResponse err = new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<?> handlerNotFoundException(InternalServerException ex, WebRequest request) {
        //Log err
        ErrorResponse err = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
