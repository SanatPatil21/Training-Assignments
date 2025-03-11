package com.example.demo.models;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Clerk extends Employee {
	
	public Clerk() {
        super(20000, "Clerk");
    }
 
 public void raiseSalary() {
        salary += 10000;
    }


}
