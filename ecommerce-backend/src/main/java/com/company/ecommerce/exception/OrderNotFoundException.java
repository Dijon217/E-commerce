package com.company.ecommerce.exception;

public class OrderNotFoundException extends Exception{

    public OrderNotFoundException(String msg){
        super(msg);
    }
}
