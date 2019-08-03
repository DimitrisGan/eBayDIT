package com.ted.eBayDIT.exception;

public class UserServiceException extends RuntimeException{

    private static final long serialVersionUID = 7115916491951402824L;


    public UserServiceException(String message) {
        super(message);
    }
}
