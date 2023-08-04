package com.twoday.internshipwarehouse.exceptions;

public class ProductNotFoundByIdException extends RuntimeException {

    public ProductNotFoundByIdException(int id) {
        super(String.format("Product with ID %d not found", id));
    }
}
