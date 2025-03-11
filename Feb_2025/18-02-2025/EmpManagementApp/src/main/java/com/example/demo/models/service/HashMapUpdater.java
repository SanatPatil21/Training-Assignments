package com.example.demo.models.service;

import java.util.HashMap;

import com.example.demo.models.Employee;

public class HashMapUpdater {
    public static HashMap<Integer,Employee> empMap = new HashMap<>();

    public static void updateHashMap(Employee employeeObject) {
        empMap.put(employeeObject.getId(), employeeObject);
        System.out.println("Added to Hashmap");
    }

    public static Employee searchHashMap(int id) {
        if (empMap.containsKey(id)) {
            Employee employee = (Employee) empMap.get(id);
            return employee;
        }
        return null;
    }

    public static void removeEmployee(int id) {
        if (empMap.containsKey(id)) {
            empMap.remove(id);
        }
    }
}
