package org.example.exception;

import jakarta.ws.rs.WebApplicationException;

public class MyException extends WebApplicationException {
    public MyException() {
        super();
    }
    public MyException(String message) {
        super("Error: " +message);
    }
}