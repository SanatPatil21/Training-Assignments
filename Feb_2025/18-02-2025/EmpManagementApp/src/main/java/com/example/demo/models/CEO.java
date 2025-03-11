package com.example.demo.models;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.example.demo.exceptions.CEOAlreadyRegisteredException;
import com.example.demo.models.service.HashMapUpdater;

//MAKE THIS SINGLETON
public class CEO extends Employee {
    private static CEO ceo = null;

    CEO() {
        super(1000000, "CEO");
    }

    public void raiseSalary() {
        salary += 100000;
    }

    public static CEO registerCEO() {

        try {
            if(HashMapUpdater.empMap.containsKey(1))
                throw new CEOAlreadyRegisteredException();
            else
                ceo = new CEO();
        } catch (CEOAlreadyRegisteredException e) {
            // TODO: handle exception
            e.displayMessage();
        }
        return ceo;

    }	
}