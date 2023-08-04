package com.twoday.internshipwarehouse.exceptions;

import com.twoday.internshipwarehouse.records.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler({InsufficientQuantityException.class})
    public ResponseEntity<ErrorMessage> handleInsufficientQuantityException(
            InsufficientQuantityException exception
    ) {
        return new ResponseEntity<>(
                new ErrorMessage(exception.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidValueException.class})
    public ResponseEntity<ErrorMessage> handleInvalidValueException(
            InvalidValueException exception
    ) {
        return new ResponseEntity<>(
                new ErrorMessage(exception.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ProductNotFoundByIdException.class})
    public ResponseEntity<ErrorMessage> handleProductNotFoundByIdException(
            ProductNotFoundByIdException exception
    ) {
        return new ResponseEntity<>(
                new ErrorMessage(exception.getMessage()),
                HttpStatus.NOT_FOUND);
    }
}