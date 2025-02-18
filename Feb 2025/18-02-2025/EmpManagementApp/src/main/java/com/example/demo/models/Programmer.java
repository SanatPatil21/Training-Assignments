package com.example.demo.models;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Programmer extends Employee {
	 public Programmer() {
	        super(50000, "Programmer");
	    }
	 
	 public void raiseSalary() {
	        salary += 10000;
	    }
	 
//	 public static void addProgrammer() {
//	        new Programmer();
//	    }

}

