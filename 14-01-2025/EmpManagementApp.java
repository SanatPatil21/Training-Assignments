package emp.assignment;

//14-01-2025: Improved the Exception Handling code with using Regex to add Name ID and Age Validation
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.*;;


abstract class Employee {
    String name;
    int age;
    String designation;
    float salary;
    int id;
    static Employee[] emparray = new Employee[100];
    static int empCount = 0;
    Scanner sc = new Scanner(System.in);

    Employee(float salary, String designation) {      
        //All validator methods from all the READER CLASSES handle the Reading and Validation of Employee Details.
        this.id=IdReader.idValidator("Employeee");
        this.name = NameReader.nameValidator();
        this.age=AgeReader.ageValidator(21, 60);
        this.salary = salary;
        this.designation = designation;

        if (empCount < emparray.length) {
            emparray[empCount++] = this;
        }
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

final class Clerk extends Employee {
    Clerk() {
        super(10000, "Clerk");
    }

    public void raiseSalary() {
        salary += 5000;
    }
}

class Programmer extends Employee {
    Programmer() {
        super(50000, "Programmer");
    }

    public void raiseSalary() {
        salary += 10000;
    }
}

class Manager extends Employee {
    Manager() {
        super(100000, "Manager");
    }

    public void raiseSalary() {
        salary += 10000;
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
            System.out.println("5. Exit");
            System.out.println("-------------------------------------");
            // System.out.print("Enter your choice: ");
            mainMenuChoice = Menu.validateChoice(5);

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
                            new Clerk();
                        } else if (employeeType == 2) {
                            System.out.println("Adding Details of Programmer");
                            new Programmer();
                        } else if (employeeType == 3) {
                            System.out.println("Adding Details of Manager");
                            new Manager();
                        }
                    } while (employeeType != 4);
                    break;
                case 2:
                    System.out.println("Displaying all employees:");
                    for (int i = 0; i < Employee.empCount; i++) {
                        Employee.emparray[i].display();
                    }
                    break;

                case 3:
                    System.out.println("Raising salaries of all employees:");
                    for (int i = 0; i < Employee.empCount; i++) {
                        Employee.emparray[i].raiseSalary();
                    }
                    break;

                case 4:
                    System.out.print("Enter the employee ID to delete: ");
                    int deleteEmployeeInput = sc.nextInt();
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

                case 5:
                    System.out.println("Exiting the application...");
                    break;

            }

        } while (mainMenuChoice != 5);

        sc.close();
    }
}



class InvalidNameException extends RuntimeException{
    InvalidNameException(){
        super();
    }

    InvalidNameException(String msg){
        super(msg);
    }

    public void displayMessage(){
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

class DuplicateIdException extends RuntimeException{
    DuplicateIdException(){
        super();
    }
    DuplicateIdException(String msg){
        super(msg);
    }

    public void displayMessage(){
        System.out.println("Employee ID already exists. Please enter a unique ID.");
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
                if (choice < 1 || choice > maxChoice)
                    throw new InvalidChoiceException();
                return choice;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a numeric value only ");
            } catch (InvalidChoiceException e) {
                e.displayMessage(maxChoice);
            }
        }

    }

}


//We can have one class to read all details of empolyees otherwise we can have different classes for Age, Name, ID etc. So it can be more reusable. If we need more finetuned logic for Employee Management System then we can use a single class itself.


class IdReader{
    public static int idValidator(String role){
        System.out.println("Please enter an "+role+" ID:");
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                int inputId = sc.nextInt();
                for (int i = 0; i < Employee.empCount; i++) {
                    //This is used specifically for employee
                    if (Employee.emparray[i].id == inputId) {
                        throw new DuplicateIdException();
                    }
                }
                sc.close();
                return inputId;
            } catch (InputMismatchException e) {
                System.out.println("Please enter numeric value");
            } catch (DuplicateIdException e){
                e.displayMessage();
            }
            
        }
        
    }
    
}

//This reader can be reused for Other Projects and Other Use Cases as well
class AgeReader{
    //Handler to Read and Validate Age
    public static int ageValidator(int lowerLimit, int upperLimit) {
        while(true){
            try {
                System.out.println("Please Enter your age: ");
                int ageInput = new Scanner(System.in).nextInt();
                if (ageInput > upperLimit || ageInput < lowerLimit) {
                    throw new InvalidAgeInputException("Please enter age between "+lowerLimit+" and "+upperLimit);
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

//Same way a common Name Reader which can be reused
class NameReader{
    public static String nameValidator(){
        System.out.println("Please Enter your name: ");
        System.out.println("Note: First and Last name required, both Starting with Captial Letters");
        Pattern p1 = Pattern.compile("^[A-Z][a-z]+\\s[A-Z][a-z]+$");
        while(true){
            try {
                // System.out.println("Please enter yor name: ");
                String inputName = new Scanner(System.in).nextLine();
                Matcher m1 = p1.matcher(inputName);
                if(!m1.matches())
                    throw new InvalidNameException();
                return inputName;          
            } catch (InvalidNameException e) {
                e.displayMessage();
            }
        }
    }

}