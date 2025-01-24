package emp.assignment;

import java.io.*;
import java.security.cert.CRLException;
import java.util.regex.*;
//24-01-2025: Update the whole code to now Apply serialization and deserialization to persist the object data
import java.util.*;

abstract class Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    // Unique ID to each employee

    protected int id;
    protected String name;
    protected int age;
    protected double salary;
    protected String designation;
    // Chnaged all to protected to access from child classes

    public Employee(int id, String name, int age, double salary, String designation) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.designation = designation;
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

    // Getters kyuki protected
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getSalary() {
        return salary;
    }

    public String getDesignation() {
        return designation;
    }

}

class Clerk extends Employee {
    Clerk(int id, String name, int age, double salary) {
        super(id, name, age, salary, "Clerk");
    }

    public void raiseSalary() {
        salary += 5000;
    }

    public static Clerk addClerk() {
        int id = IdReader.idValidator("Employee");
        String name = NameReader.nameValidator();
        int age = AgeReader.ageValidator(21, 60);
        double salary = 10000;
        return new Clerk(id, name, age, salary);
    }
}

class Programmer extends Employee {
    Programmer(int id, String name, int age, double salary) {
        super(id, name, age, salary, "Programmer");
    }

    public void raiseSalary() {
        salary += 10000;
    }

    public static Programmer addProgrammer() {
        int id = IdReader.idValidator("Employee");
        String name = NameReader.nameValidator();
        int age = AgeReader.ageValidator(21, 60);
        double salary = 50000;
        return new Programmer(id, name, age, salary);
    }
}

class Manager extends Employee {
    Manager(int id, String name, int age, double salary) {
        super(id, name, age, salary, "Manager");
    }

    public void raiseSalary() {
        salary += 10000;
    }

    public static Manager addManager() {
        int id = IdReader.idValidator("Employee");
        String name = NameReader.nameValidator();
        int age = AgeReader.ageValidator(21, 60);
        double salary = 100000;
        return new Manager(id, name, age, salary);
    }

}

final class CEO extends Employee {
    private static CEO ceo = null;

    CEO(int id, String name, int age, double salary) {
        super(id, name, age, salary, "CEO");
    }

    public void raiseSalary() {
        salary += 100000;
    }

    public static CEO registerCEO() {
        if (EmpManagementApp.employees.containsKey(1) == false) {
            System.out.println("Registering a new CEO");
            int id = 1;
            String name = NameReader.nameValidator();
            int age = AgeReader.ageValidator(21, 60);
            double salary = 1000000;
            ceo = new CEO(id, name, age, salary);
            EmpManagementApp.employees.put(id, ceo);
        } else {
            System.out.println("CEO already registered");
        }
        return ceo;
    }
}

class EmployeeCreate {
    public static Employee getEmployee(String designation) {
        Employee employeeObject = null;
        switch (designation) {
            case "Clerk":
                employeeObject = Clerk.addClerk();
                EmpManagementApp.employees.put(employeeObject.id, employeeObject);
                break;
            case "Programmer":
                employeeObject = Programmer.addProgrammer();
                EmpManagementApp.employees.put(employeeObject.id, employeeObject);
                break;
            case "Manager":
                employeeObject = Manager.addManager();
                EmpManagementApp.employees.put(employeeObject.id, employeeObject);
                break;
        }
        return employeeObject;

    }

}
/*
//OLDER Classes to handle CSV STUFF
class CSVWriter {
    public static void writeToCSV(Collection<Employee> employees) {
        System.out.println("Updating to CSV");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("employees.csv"))) {
            for (Employee employee : employees) {
                writer.write(employee.getId() + "," +
                        employee.getName() + "," +
                        employee.getAge() + "," +
                        employee.getSalary() + "," +
                        employee.getDesignation());
                writer.newLine();
            }
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

}

class CSVReader {
    public static Map<Integer, Employee> readFromCSV() {
        Map<Integer, Employee> employees = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("employees.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // String[] values = line.split(",");
                // Instead of the split we can also use StringTokenizer
                StringTokenizer tokenizer = new StringTokenizer(line, ",");
                // Create an array of strings with the size equal to the number of tokens in the
                // line
                String[] values = new String[tokenizer.countTokens()];
                int index = 0;
                while (tokenizer.hasMoreTokens()) {
                    values[index++] = tokenizer.nextToken();
                }
                Employee employeeObject = createEmployee(values);
                if (employeeObject != null) {
                    employees.put(employeeObject.getId(), employeeObject);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return employees;

    }

    private static Employee createEmployee(String[] values) {
        int id = Integer.parseInt(values[0]);
        String name = values[1];
        int age = Integer.parseInt(values[2]);
        double salary = Double.parseDouble(values[3]);
        String designation = values[4];

        switch (designation) {
            case "Clerk":
                return new Clerk(id, name, age, salary);
            case "Programmer":
                return new Programmer(id, name, age, salary);
            case "Manager":
                return new Manager(id, name, age, salary);
            case "CEO":
                return new CEO(id, name, age, salary);
            default:
                return null;
        }

    }
}
 */
    
class SerializationUtil {
    public static void serializeEmployees(Map<Integer, Employee> employees, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(employees);
            System.out.println("Employees serialized to " + fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<Integer, Employee> deserializeEmployees(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (Map<Integer, Employee>) ois.readObject();

        }catch(EOFException e){
            return new HashMap<>();
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new HashMap<>();
        } 
    }
}

class NameSorter implements Comparator<Employee> {
    public int compare(Employee e1, Employee e2) {
        return e1.name.compareTo(e2.name);
    }

}

class AgeSorter implements Comparator<Employee> {
    public int compare(Employee e1, Employee e2) {
        return Integer.compare(e1.age, e2.age);
    }
}

class IdSorter implements Comparator<Employee> {
    public int compare(Employee e1, Employee e2) {
        return Integer.compare(e1.id, e2.id);
    }
}

class SalarySorter implements Comparator<Employee> {
    public int compare(Employee e1, Employee e2) {
        return Double.compare(e1.salary, e2.salary);
    }
}

class DesignationSorter implements Comparator<Employee> {
    public int compare(Employee e1, Employee e2) {
        return e1.designation.compareTo(e2.designation);
    }

}

class Display {
    public static void displaySortedBy(Comparator<Employee> comparator, Collection<Employee> employees) {
        List<Employee> list = new ArrayList<>(employees);
        Collections.sort(list, comparator);
        for (Employee employee : list) {
            employee.display();
        }
    }

}

class Search {
    public static void searchByName() {
        String inputName = new Scanner(System.in).nextLine();
        boolean found = false;

        for (Employee emp : EmpManagementApp.employees.values()) {
            if (emp.name.equalsIgnoreCase(inputName)) {
                emp.display();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No employees found with the name: " + inputName);
        }
    }

    public static void searchById() {
        try {
            int inputId = new Scanner(System.in).nextInt();
            Employee employee = EmpManagementApp.employees.get(inputId);
            if (employee != null) {
                employee.display();
            } else {
                System.out.println("Employee with ID " + inputId + " not found.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Enter numeric value only");
        }
    }

    public static void searchByDesignation() {
        System.out.print("Enter Designation to search: ");
        String inputDesignation = new Scanner(System.in).next();

        boolean found = false;

        for (Employee employee : EmpManagementApp.employees.values()) {
            if (employee.designation.equalsIgnoreCase(inputDesignation)) {
                employee.display();
                found = true;
            }

        }

        if (!found) {
            System.out.println("No employees found with the designation: " + inputDesignation);
        }
    }

}

public class EmpManagementApp {
    public static Map<Integer, Employee> employees = new HashMap<>();

    public static void main(String args[]) throws InputMismatchException {
        Scanner sc = new Scanner(System.in);
        int mainMenuChoice = 0, employeeType = 0, sortingInput = 0, searchMenuInput = 0;

        System.out.println("Welcome to Employee Management App!");
        // Check if employees.csv file exists, if not create
        File serFile = new File("employees.ser");
        if (!serFile.exists()) {
            try {
                serFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            employees = SerializationUtil.deserializeEmployees("employees.ser");

            do {
                System.out.println("-------------------------------------");
                System.out.println("MAIN MENU");
                System.out.println("1. Create Employee");
                System.out.println("2. Display");
                System.out.println("3. Raise Salary");
                System.out.println("4. Remove Employee");
                System.out.println("5. Search Employee");
                System.out.println("6. Exit");
                System.out.println("8: Save to SER");
                if (!employees.containsKey(1))
                    System.out.println("7. Register CEO");

                System.out.println("-------------------------------------");
                // System.out.print("Enter your choice: ");
                mainMenuChoice = Menu.validateChoice(8);

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
                            Employee employeeObject = null;
                            if (employeeType == 1) {
                                System.out.println("Adding Details of Clerk");
                                employeeObject = EmployeeCreate.getEmployee("Clerk");
                                // CSVWriter.writeToCSV(employees.values());

                            } else if (employeeType == 2) {
                                System.out.println("Adding Details of Programmer");
                                employeeObject = EmployeeCreate.getEmployee("Programmer");
                                // new Programmer();
                            } else if (employeeType == 3) {
                                System.out.println("Adding Details of Manager");
                                // new Manager();
                                employeeObject = EmployeeCreate.getEmployee("Manager");
                            }

                        } while (employeeType != 4);
                        break;
                    case 2:
                        System.out.println("How do you want to Display:");
                        do {

                            System.out.println("---------------------------------------------");
                            System.out.println("1. By Name");
                            System.out.println("2. By ID");
                            System.out.println("3. By Designation");
                            System.out.println("4. By Salary");
                            System.out.println("5. By Age");
                            System.out.println("6. Display All");
                            System.out.println("7. Back");
                            System.out.println("--------------------------------------------");

                            sortingInput = Menu.validateChoice(7);

                            switch (sortingInput) {
                                case 1:
                                    System.out.println("Sorting by Name");
                                    // NEW DISPLAY METHOD
                                    Display.displaySortedBy(new NameSorter(), employees.values());
                                    // Display.displaySortedBy(new NameSorter());
                                    break;

                                case 2:
                                    System.out.println("Sorting by ID");
                                    Display.displaySortedBy(new IdSorter(), employees.values());

                                    break;
                                case 3:
                                    System.out.println("Sorting by Designation");

                                    Display.displaySortedBy(new DesignationSorter(), employees.values());

                                    break;
                                case 4:
                                    System.out.println("Sorting by Salary");

                                    Display.displaySortedBy(new SalarySorter(), employees.values());

                                    break;
                                case 5:
                                    System.out.println("Sorting by Age");
                                    Display.displaySortedBy(new AgeSorter(), employees.values());

                                    break;

                                case 6:
                                    for (Employee employee : employees.values()) {
                                        employee.display();
                                    }
                                    break;

                            }
                        } while (sortingInput != 7);
                        break;

                    case 3:
                        System.out.println("Raising salaries of all employees:");
                        for (Employee employee : employees.values()) {
                            employee.raiseSalary();
                        }
                        // CSVWriter.writeToCSV(employees.values());
                        break;

                    case 4:
                        System.out.print("Enter the employee ID to delete: ");
                        int deleteEmployeeInput = IdReader.idReader();

                        Employee emp = employees.get(deleteEmployeeInput);
                        if (emp != null) {
                            emp.display();
                            System.out.print("Are you sure you want to delete this Employee? (Y/N): ");
                            char confirmationInput = sc.next().charAt(0);

                            if (confirmationInput == 'Y' || confirmationInput == 'y') {
                                employees.remove(deleteEmployeeInput);
                                // CSVWriter.writeToCSV(employees.values());
                                System.out.println("Employee successfully removed.");
                            } else {
                                System.out.println("Employee removal cancelled.");
                            }
                        } else {
                            System.out.println("Employee ID not found.");
                        }
                        break;

                    case 5:
                        System.out.println("SEARCH MENU");
                        do {
                            System.out.println("How do you want to search: ");

                            System.out.println("---------------------------------------------");
                            System.out.println("1. Name");
                            System.out.println("2. ID");
                            System.out.println("3. Designation");
                            System.out.println("4. Back");

                            System.out.println("--------------------------------------------");

                            searchMenuInput = Menu.validateChoice(4);

                            switch (searchMenuInput) {
                                case 1:
                                    System.out.println("Search Results by Name");
                                    Search.searchByName();
                                    break;
                                case 2:
                                    System.out.println("Search Results by ID");
                                    Search.searchById();
                                    break;
                                case 3:
                                    System.out.println("Search Results by Designation");
                                    Search.searchByDesignation();
                                    break;
                            }
                        } while (searchMenuInput != 4);
                        break;

                    case 6:
                        System.out.println("Exiting the application...");
                        break;
                    case 7:
                        CEO.registerCEO();
                        SerializationUtil.serializeEmployees(employees, "employees.ser");
                        break;
                    case 8:
                        SerializationUtil.serializeEmployees(employees, "employees.ser");
                        System.out.println("Data Saved to CSV");

                        break;
                }

            } while (mainMenuChoice != 6);
        }finally {
            SerializationUtil.serializeEmployees(employees, "employees.ser");
            sc.close();
        }
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
    private static boolean ceoExists = false;

    public static int validateChoice(int max) {
        maxChoice = max;
        while (true) {
            System.out.println("Enter Choice:- ");
            try {
                int choice = new Scanner(System.in).nextInt();
                if (choice < 1 || choice > maxChoice) {
                    throw new InvalidChoiceException();
                }
                // Check if there is a CEO or not
                else if ((EmpManagementApp.employees.containsKey(1) == false) && choice != 7) {
                    throw new NoCEOException();
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
    public static int idReader() {
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
                if (EmpManagementApp.employees.containsKey(inputId)) {
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
