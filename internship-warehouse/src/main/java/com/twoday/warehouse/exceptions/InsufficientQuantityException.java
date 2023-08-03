package com.twoday.warehouse.exceptions;

public class InsufficientQuantityException extends RuntimeException {

    public InsufficientQuantityException(int id) {
        super(String.format("Insufficient quantity for item with ID %d", id));
    }
}
