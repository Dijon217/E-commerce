package com.company.ecommerce.exception;

public class ProductNotExistException extends Exception{
    public ProductNotExistException(String msg) {
        super(msg);
    }
}
