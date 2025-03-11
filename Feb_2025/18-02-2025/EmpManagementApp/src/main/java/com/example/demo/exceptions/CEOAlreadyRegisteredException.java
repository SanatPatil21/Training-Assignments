package com.example.demo.exceptions;

public class CEOAlreadyRegisteredException extends RuntimeException {
    public CEOAlreadyRegisteredException() {
        super();
    }

    CEOAlreadyRegisteredException(String msg) {
        super(msg);
    }

    public void displayMessage() {
        System.out.println("CEO already exists.");
    }

}