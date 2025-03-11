package com.example.demo.models;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Manager extends Employee {
    public Manager() {
        super(100000, "Manager");
    }

    public void raiseSalary() {
        salary += 10000;
    }
}
