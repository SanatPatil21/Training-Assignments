package com.example.demo.exceptions;

public class NoCEOException extends Exception {
    public NoCEOException() {
        super();
    }

    NoCEOException(String msg) {
        super(msg);
    }

    public void displayMessage() {
        System.out.println("CEO does not exsits");
    }
}