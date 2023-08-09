package com.twoday.internshipshop.exceptions;

import com.twoday.internshipmodel.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<ErrorMessage> handleBadRequestException(
            BadRequestException exception
    ) {
        return new ResponseEntity<>(
                new ErrorMessage(exception.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UnknownException.class})
    public ResponseEntity<ErrorMessage> handleUnknownException(
            UnknownException exception
    ) {
        return new ResponseEntity<>(
                new ErrorMessage(exception.getMessage()),
                HttpStatus.BAD_REQUEST);
    }
}
