package com.company.ecommerce.exception;

public class AuthenticationFailException extends Exception{

    public AuthenticationFailException(String msg){
        super(msg);
    }
}
