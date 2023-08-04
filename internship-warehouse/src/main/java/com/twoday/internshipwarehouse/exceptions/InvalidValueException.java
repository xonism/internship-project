package com.twoday.internshipwarehouse.exceptions;

public class InvalidValueException extends RuntimeException {

    public InvalidValueException(String propertyName) {
        super(String.format("Invalid value provided for %s", propertyName));
    }
}
