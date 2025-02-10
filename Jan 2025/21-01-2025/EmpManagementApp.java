package emp.assignment;

import java.security.cert.CRLException;
import java.util.HashMap;
//21-01-2025: Changed the code to use Collection Framework
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.*;
import java.util.Map;

abstract class Employee {
    String name;
    int age;
    String designation;
    float salary;
    int id;
    static Employee[] emparray = new Employee[100];
    static int empCount = 0;
    // Scanner sc = new Scanner(System.in);

    Employee(float salary, String designation) {
        if (designation == "CEO")
            this.id = 1;
        else
            this.id = IdReader.idValidator("Employeee");
        this.name = NameReader.nameValidator();
        this.age = AgeReader.ageValidator(21, 60);
        this.salary = salary;
        this.designation = designation;

        // This is the Previous code where the newly created employee object was added
        // to the array
        if (empCount < emparray.length) {
            emparray[empCount++] = this;
        }
        // New code to handle HashMaps
        hashMapUpdater.updateHashMap(this);

    }

    public void display() {
        System.out.println("Employee Id: " + id);
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Salary: " + salary);
        System.out.println("Designation: " + designation);
        System.out.println();
    }

    public abstract void raiseSalary();

}

class hashMapUpdater {
    static HashMap empMap = new HashMap();

    public static void updateHashMap(Employee employeeObject) {
        empMap.put(employeeObject.id, employeeObject);
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

class Clerk extends Employee {
    Clerk() {
        super(10000, "Clerk");
    }

    public void raiseSalary() {
        salary += 5000;
    }

    public static void addClerk() {
        new Clerk();
    }
}

class Programmer extends Employee {
    Programmer() {
        super(50000, "Programmer");
    }

    public void raiseSalary() {
        salary += 10000;
    }

    public static void addProgrammer() {
        new Programmer();
    }
}

class Manager extends Employee {
    Manager() {
        super(100000, "Manager");
    }

    public void raiseSalary() {
        salary += 10000;
    }

    public static void addManager() {
        new Manager();
    }
}

final class CEO extends Employee {
    private static CEO ceo = null;

    CEO() {
        super(1000000, "CEO");
    }

    public void raiseSalary() {
        salary += 100000;
    }

    public static CEO registerCEO() {

        try {
            /*
            //OLDER CODE WHEN ARRAY WAS USED
            if (Employee.emparray[0] == null) {
                ceo = new CEO();
            } else
                throw new CEOAlreadyRegisteredException();
            */
            if(hashMapUpdater.empMap.containsKey(1))
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

class EmployeeCreate {
    public static Employee getEmployee(String designation) {
        // Employee employeeObject = null;
        switch (designation) {
            case "Clerk":
                Clerk.addClerk();
                break;
            case "Programmer":
                Programmer.addProgrammer();
                break;
            case "Manager":
                Manager.addManager();
                break;
        }
        return null;

    }

}

public class EmpManagementApp {
    public static void main(String args[]) throws InputMismatchException {
        Scanner sc = new Scanner(System.in);
        int mainMenuChoice = 0, employeeType = 0;

        System.out.println("Welcome to Employee Management App!");

        do {
            System.out.println("-------------------------------------");
            System.out.println("MAIN MENU");
            System.out.println("1. Create Employee");
            System.out.println("2. Display");
            System.out.println("3. Raise Salary");
            System.out.println("4. Remove Employee");
            System.out.println("5. Search Employee");
            System.out.println("6. Exit");
            if (!hashMapUpdater.empMap.containsKey(1))
                System.out.println("7. Register CEO");
 
            System.out.println("-------------------------------------");
            // System.out.print("Enter your choice: ");
            mainMenuChoice = Menu.validateChoice(7);
            Iterator it = hashMapUpdater.empMap.entrySet().iterator();
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
                            EmployeeCreate.getEmployee("Clerk");

                        } else if (employeeType == 2) {
                            System.out.println("Adding Details of Programmer");
                            EmployeeCreate.getEmployee("Programmer");
                            // new Programmer();
                        } else if (employeeType == 3) {
                            System.out.println("Adding Details of Manager");
                            // new Manager();
                            EmployeeCreate.getEmployee("Manager");
                        }
                    } while (employeeType != 4);
                    break;
                case 2:
                    System.out.println("Displaying all employees:");
                    /*
                     * //OLDER CODE USING ARRAY
                     * for (int i = 0; i < Employee.empCount; i++) {
                     * Employee.emparray[i].display();
                     * }
                     */

                    while (it.hasNext()) {
                        Map.Entry m = (Map.Entry) it.next();
                        // Display all the details of employee
                        Employee emp = (Employee) m.getValue();
                        emp.display();
                    }
                    break;

                case 3:
                    System.out.println("Raising salaries of all employees:");
                    /*
                       //OLDER CODE WHEN ARRAY WAS USED
                       for (int i = 0; i < Employee.empCount; i++) {
                       Employee.emparray[i].raiseSalary();
                       }
                    */
                    while (it.hasNext()) {
                        Map.Entry m = (Map.Entry) it.next();
                        Employee emp = (Employee) m.getValue();
                        emp.raiseSalary();
                    }
                    break;

                case 4:
                    System.out.print("Enter the employee ID to delete: ");
                    int deleteEmployeeInput = IdReader.idReader();
                    /*
                    //OLDER CODE WHEN ARRAY WAS USED
                    boolean found = false;

                    for (int i = 0; i < Employee.empCount; i++) {
                        if (Employee.emparray[i].id == deleteEmployeeInput) {
                            Employee.emparray[i].display();
                            System.out.print("Are you sure you want to delete this Employee? (Y/N): ");
                            char confirmationInput = sc.next().charAt(0);

                            if (confirmationInput == 'Y' || confirmationInput == 'y') {
                                Employee.emparray[i] = null;
                                Employee.empCount--;
                                System.out.println("Employee successfully removed.");
                            } else {
                                System.out.println("Employee removal cancelled.");
                            }

                            found = true;

                            break;
                        }
                    }

                    if (!found) {
                        System.out.println("Employee ID not found.");
                    }
                    break;
                    */
                    Employee emp = hashMapUpdater.searchHashMap(deleteEmployeeInput);
                    if(emp != null) {
                        emp.display();
                        System.out.print("Are you sure you want to delete this Employee? (Y/N): ");
                        char confirmationInput = sc.next().charAt(0);

                        if (confirmationInput == 'Y' || confirmationInput == 'y') {
                            hashMapUpdater.removeEmployee(deleteEmployeeInput);
                            System.out.println("Employee successfully removed.");
                        } else {
                            System.out.println("Employee removal cancelled.");
                        }
                    }else {
                        System.out.println("Employee ID not found.");
                    }
                    break;

                case 5:
                    //SEARCH OPTION
                    System.out.print("Enter the employee ID to search: ");
                    int searchInput = IdReader.idReader();
                    Employee emp2 = hashMapUpdater.searchHashMap(searchInput);
                    if(emp2 != null) {
                        System.out.println("DETAILS OF EMPLOYEE ID: "+searchInput);
                        emp2.display();
                        break;
                    }else {
                        System.out.println("Employee ID not found.");
                        break;
                    }
                    
                    
                    
                case 6:
                    System.out.println("Exiting the application...");
                    break;
                case 7:
                    System.out.println("Registering a new CEO");
                    CEO.registerCEO();
                    break;

            }

        } while (mainMenuChoice != 6);

        sc.close();
    }
}

class NoCEOException extends RuntimeException {
    NoCEOException() {
        super();
    }

    NoCEOException(String msg) {
        super(msg);
    }

    public void displayMessage() {
        System.out.println("CEO does not exsits");
    }
}

class InvalidNameException extends RuntimeException {
    InvalidNameException() {
        super();
    }

    InvalidNameException(String msg) {
        super(msg);
    }

    public void displayMessage() {
        System.out.println("Please enter Name according to Given Format.");
    }

}

class InvalidAgeInputException extends RuntimeException {
    InvalidAgeInputException() {
        super();
    }

    InvalidAgeInputException(String msg) {
        super(msg);
    }
}

class InvalidChoiceException extends RuntimeException {
    InvalidChoiceException() {
        super();
    }

    InvalidChoiceException(String msg) {
        super(msg);
    }

    public void displayMessage(int maxChoice) {
        System.out.println("Please enter a choice between 1 and " + maxChoice);
    }

}

class DuplicateIdException extends RuntimeException {
    DuplicateIdException() {
        super();
    }

    DuplicateIdException(String msg) {
        super(msg);
    }

    public void displayMessage() {
        System.out.println("Employee ID already exists. Please enter a unique ID.");
    }
}

// Exception for when Someone tries to re register CEO
class CEOAlreadyRegisteredException extends RuntimeException {
    CEOAlreadyRegisteredException() {
        super();
    }

    CEOAlreadyRegisteredException(String msg) {
        super(msg);
    }

    public void displayMessage() {
        System.out.println("CEO already exists.");
    }

}

// We create a class to seperate out the try-catch block from the main methods
class Menu {
    private static int maxChoice;

    public static int validateChoice(int max) {
        maxChoice = max;
        while (true) {
            System.out.println("Enter Choice:- ");
            try {
                int choice = new Scanner(System.in).nextInt();
                /*
                 * //OLDER CODE WHEN ARRAY WAS USED
                if (Employee.emparray[0] == null && choice != 7 && choice != 6) {
                    throw new NoCEOException();
                }
                if (Employee.emparray[0] != null && choice == 7) {
                    throw new CEOAlreadyRegisteredException();
                }
                if (choice < 1 || choice > maxChoice)
                    throw new InvalidChoiceException();
                */
                
                //Check if there is a CEO or not
                if(!(hashMapUpdater.empMap.containsKey(1)) && choice != 7 && choice != 6) {
                    throw new NoCEOException();
                }
                if((hashMapUpdater.empMap.containsKey(1)) && choice == 7) {
                    throw new CEOAlreadyRegisteredException();
                }
                if(choice < 1 || choice > maxChoice) {
                    throw new InvalidChoiceException();
                }
                
                return choice;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a numeric value only ");
            } catch (NoCEOException e) {
                e.displayMessage();
            } catch (CEOAlreadyRegisteredException e) {
                e.displayMessage();
            } catch (InvalidChoiceException e) {
                e.displayMessage(maxChoice);
            }
        }

    }

}

// We can have one class to read all details of empolyees otherwise we can have
// different classes for Age, Name, ID etc. So it can be more reusable. If we
// need more finetuned logic for Employee Management System then we can use a
// single class itself.

class IdReader {
    public static int idReader(){
        while (true) {
            try {
                int inputId = new Scanner(System.in).nextInt();
                return inputId;
            } catch (InputMismatchException e) {
                System.out.println("Please enter numeric value");
            }
        }
    }
    public static int idValidator(String role) {
        System.out.println("Please enter an " + role + " ID:");
        // Modified code for ID validator considering HashMap used for employee

        while (true) {
            try {
                int inputId = new Scanner(System.in).nextInt();
                /*
                 * //OLDER CODE FOR WHEN ARRAY WAS USED
                 * for (int i = 0; i < Employee.empCount; i++) {
                 * // This is used specifically for employee
                 * if (Employee.emparray[i].id == inputId) {
                 * throw new DuplicateIdException();
                 * }
                 * }
                 */
                if (hashMapUpdater.empMap.containsKey(inputId)) {
                    throw new DuplicateIdException();
                } else {
                    return inputId;
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter numeric value");
            } catch (DuplicateIdException e) {
                e.displayMessage();
            }

        }
    }

}

// This reader can be reused for Other Projects and Other Use Cases as well
class AgeReader {
    // Handler to Read and Validate Age
    public static int ageValidator(int lowerLimit, int upperLimit) {
        while (true) {
            try {
                System.out.println("Please Enter your age: ");
                int ageInput = new Scanner(System.in).nextInt();
                if (ageInput > upperLimit || ageInput < lowerLimit) {
                    throw new InvalidAgeInputException("Please enter age between " + lowerLimit + " and " + upperLimit);
                }
                return ageInput;
            } catch (InputMismatchException e) {
                System.out.println("Please enter numeric value");
                // sc.nextLine();
            } catch (InvalidAgeInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

// Same way a common Name Reader which can be reused
class NameReader {
    public static String nameValidator() {
        System.out.println("Please Enter your name: ");
        System.out.println("Note: First and Last name required, both Starting with Capital Letters");
        Pattern p1 = Pattern.compile("^[A-Z][a-z]+\\s[A-Z][a-z]+$");
        while (true) {
            try {
                String inputName = new Scanner(System.in).nextLine();
                Matcher m1 = p1.matcher(inputName);
                if (!m1.matches()) {
                    throw new InvalidNameException();
                }
                return inputName;
            } catch (InvalidNameException e) {
                System.out.println("Invalid name format. Please try again.");
            } catch (NoSuchElementException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
