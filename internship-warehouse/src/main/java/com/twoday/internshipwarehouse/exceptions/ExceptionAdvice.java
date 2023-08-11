package com.twoday.internshipwarehouse.exceptions;

import com.twoday.internshipmodel.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.FileNotFoundException;
import java.time.format.DateTimeParseException;

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

    @ExceptionHandler({FileNotFoundException.class})
    public ResponseEntity<ErrorMessage> handleFileNotFoundException(
            FileNotFoundException exception
    ) {
        String message = "Requested file not found";
        return new ResponseEntity<>(
                new ErrorMessage(message),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({DateTimeParseException.class})
    public ResponseEntity<ErrorMessage> handleDateTimeParseException(
            DateTimeParseException exception
    ) {
        String message = String.format("Provided LocalDateTime '%s' is invalid", exception.getParsedString());
        return new ResponseEntity<>(
                new ErrorMessage(message),
                HttpStatus.BAD_REQUEST);
    }
}
