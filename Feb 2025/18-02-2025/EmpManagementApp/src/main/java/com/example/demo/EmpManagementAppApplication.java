package com.example.demo;


import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.example.demo.exceptions.Menu;
import com.example.demo.models.CEO;
import com.example.demo.models.Clerk;
import com.example.demo.models.Employee;
import com.example.demo.models.Manager;
import com.example.demo.models.Programmer;
import com.example.demo.models.service.HashMapUpdater;

@ComponentScan(basePackages = {"com.example.demo"})
@SpringBootApplication
public class EmpManagementAppApplication {

	public static void main(String[] args) {
		
		ApplicationContext ctx = SpringApplication.run(EmpManagementAppApplication.class, args);

        int mainMenuChoice = 0, employeeType = 0;
        
        System.out.println("Welcome to Employee Management App!");

        do {
            System.out.println("-------------------------------------");
            System.out.println("MAIN MENU");
            System.out.println("1. Create Employee");
            System.out.println("2. Display Employees");
            System.out.println("3. Raise Salary");
            System.out.println("4. Remove Employee");
            System.out.println("5. Search Employee");
            System.out.println("6. Exit");
            if (!HashMapUpdater.empMap.containsKey(1))
                System.out.println("7. Register CEO");

            System.out.println("-------------------------------------");

            mainMenuChoice = Menu.validateChoice(7);
            Iterator it = HashMapUpdater.empMap.entrySet().iterator();
            

            switch (mainMenuChoice) {
                case 1:
                    do {
                        System.out.println("---------------------------------------------");
                        System.out.println("1. Create Clerk");
                        System.out.println("2. Create Programmer");
                        System.out.println("3. Create Manager");
                        System.out.println("4. Back");
                        System.out.println("--------------------------------------------");

                        employeeType = Menu.validateChoice(4);

                        if (employeeType == 1) {
                            System.out.println("Adding Details of Clerk");
                            emp.
                            Clerk clerk = (Clerk) ctx.getBean("clerk");
                            HashMapUpdater.updateHashMap(clerk);
                        } else if (employeeType == 2) {
                            System.out.println("Adding Details of Programmer");
                            Programmer programmer = (Programmer) ctx.getBean("prog");
                            HashMapUpdater.updateHashMap(programmer);
                        } else if (employeeType == 3) {
                            System.out.println("Adding Details of Manager");
                            Manager manager = (Manager) ctx.getBean("manager");
                            HashMapUpdater.updateHashMap(manager);
                        }
                    } while (employeeType != 4);
                    break;

                case 2:
                    System.out.println("Displaying all employees:");
                    while (it.hasNext()) {
                        Map.Entry m = (Map.Entry) it.next();
                        Employee emp = (Employee) m.getValue();
                        System.out.println(emp.toString());
                    }
                    break;

                case 3:
                    System.out.println("Raising salaries of all employees:");
                    while (it.hasNext()) {
                        Map.Entry m = (Map.Entry) it.next();
                        Employee emp = (Employee) m.getValue();
                        emp.raiseSalary();
                    }
                    break;

                case 4:
                    System.out.print("Enter the employee ID to delete: ");
                    /*
                    int deleteEmployeeInput = sc.nextInt();
                    Employee emp = HashMapUpdater.searchHashMap(deleteEmployeeInput);
                    if (emp != null) {
                        System.out.println(emp.toString());
                        System.out.print("Are you sure you want to delete this Employee? (Y/N): ");
                        char confirmationInput = sc.next().charAt(0);

                        if (confirmationInput == 'Y' || confirmationInput == 'y') {
                            HashMapUpdater.removeEmployee(deleteEmployeeInput);
                            System.out.println("Employee successfully removed.");
                        } else {
                            System.out.println("Employee removal cancelled.");
                        }
                    } else {
                        System.out.println("Employee ID not found.");
                    }
                    */
                    break;

                case 5:
                    System.out.print("Enter the employee ID to search: ");
                    /*
                    int searchInput = sc.nextInt();
                    Employee emp2 = HashMapUpdater.searchHashMap(searchInput);
                    if (emp2 != null) {
                        System.out.println("DETAILS OF EMPLOYEE ID: " + searchInput);
                        System.out.println(emp2.toString());
                    } else {
                        System.out.println("Employee ID not found.");
                    }
                    */
                    break;

                case 6:
                    System.out.println("Exiting the application...");
                    break;

                case 7:
                    System.out.println("Registering a new CEO");
                    CEO ceo = (CEO) ctx.getBean(CEO.class);
                    HashMapUpdater.updateHashMap(ceo);
                    break;
            }
        } while (mainMenuChoice != 6);

//        sc.close();
    }
}

