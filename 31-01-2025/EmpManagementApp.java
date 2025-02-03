import java.io.*;
import java.sql.*;
import java.util.*;

import javax.sql.rowset.*;

//Use RowSet instead of ResultSet

class DatabaseConnection {

    private static final String url = "jdbc:postgresql://localhost:5432/EmpManagementApp";
    private static final String uname = "postgres";
    private static final String pwd = "tiger";
    private static Connection con=null;

    static {
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url, uname, pwd);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        try {
            if (con == null || con.isClosed()) {
                con = DriverManager.getConnection(url, uname, pwd);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to establish connection", e);
        }
        return con;
    }

    public static JdbcRowSet getRowSet(String query) {
        try {
            JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet();
            rowSet.setUrl(url);
            rowSet.setUsername(uname);
            rowSet.setPassword(pwd);
            rowSet.setCommand(query);
            // rowSet.execute();
            return rowSet;
        }catch (SQLException e) {
            throw new RuntimeException("Failed to create RowSet", e);
        }
    }
}

// Creating a sep class to handle all operations on database
class DatabaseOperations {
    /*
    public static void addEmployee(String name, int age, double salary, String designation, String department) {
        String query = "INSERT INTO employees (name, age, salary, designation, department) VALUES (?, ?, ?, ?, ?)";
        try (
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setInt(2, age);
            stmt.setDouble(3, salary);
            stmt.setString(4, designation);
            stmt.setString(5, department);
            boolean succ = stmt.execute();
            if (succ) {
                System.out.println("Employee Added Successfully");
            }

        } catch (SQLException e) {
            System.out.println("Error adding employee: " + e.getMessage());
        } 

    }
         */
    public static void addEmployee(String name, int age, double salary, String designation, String department){
        // Put all records in RowSet
        String query = "SELECT * FROM employees";  

        try (JdbcRowSet rowSet = DatabaseConnection.getRowSet(query)) {
            rowSet.execute();

            // move to the insert row
            rowSet.moveToInsertRow();
            // set the values 
            rowSet.updateString("name", name);
            rowSet.updateInt("age", age);
            rowSet.updateDouble("salary", salary);
            rowSet.updateString("designation", designation);
            rowSet.updateString("department", department);

            // insert the new row
            rowSet.insertRow();
            rowSet.moveToCurrentRow();

            
            System.out.println("Employee Added Successfully");
            
        } catch (SQLException e) {
            System.out.println("Error adding employee: " + e.getMessage());
        } 

    }

    
    public static void displayEmployees(String orderByParam) {
        String query = "SELECT * FROM employees ORDER BY " + orderByParam;
        //Modified this function to add RowSet
        //Passing the query to the function which returns us the object
        try (JdbcRowSet rowSet = DatabaseConnection.getRowSet(query)) {
            rowSet.execute();
            while (rowSet.next()) {
                System.out.println("ID: " + rowSet.getInt("eid"));
                System.out.println("Name: " + rowSet.getString("name"));
                System.out.println("Age: " + rowSet.getInt("age"));
                System.out.println("Salary: " + rowSet.getDouble("salary"));
                System.out.println("Designation: " + rowSet.getString("designation"));
                System.out.println("Department: " + rowSet.getString("department"));
                System.out.println("----------------------------");
            }

        } catch (SQLException e) {
            System.out.println(e);
        } 
    }

    public static void appraisal(int eid, double amountToBeHiked) {
        String query = "SELECT * FROM employees WHERE eid = ?";
        try (JdbcRowSet rowSet = DatabaseConnection.getRowSet(query)) {
            rowSet.setInt(1, eid);
            
            rowSet.execute();
            if (rowSet.next()) {
                double currentSalary = rowSet.getDouble("salary");
                rowSet.updateDouble("salary", currentSalary + amountToBeHiked);
                rowSet.updateRow();
                System.out.println("Salary updated successfully for Employee ID: " + eid);
            } else {
                System.out.println("Employee not found!");
            }

        } catch (SQLException e) {
            // TODO: handle exception
            System.out.println(e);
        }

    }

    public static boolean searchEmployeeById(int eid) {
        String query = "SELECT * FROM employees where eid = ?";
        try (JdbcRowSet rowSet = DatabaseConnection.getRowSet(query)) {
            rowSet.setInt(1, eid);
            rowSet.execute();
            if (rowSet != null && rowSet.next()) { // Check if there is a valid result
                System.out.println("EMPLOYEE DETAILS");
                System.out.println("ID: " + rowSet.getInt("eid"));
                System.out.println("Name: " + rowSet.getString("name"));
                System.out.println("Age: " + rowSet.getInt("age"));
                System.out.println("Salary: " + rowSet.getDouble("salary"));
                System.out.println("Designation: " + rowSet.getString("designation"));
                System.out.println("Department: " + rowSet.getString("department"));
                System.out.println("----------------------------");
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e);
        } 

        return false;
    }

    public static boolean removeEmployee(int eid) {
        String query = "DELETE FROM employees where eid = ?";
        try (
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement stmt = con.prepareStatement(query);) {
            stmt.setInt(1, eid);
            return stmt.execute();

        } catch (SQLException e) {
            System.out.println(e);
        } 
        return false;
    }

    public static void searchEmployeeByName(String name) {
        String query = "select * from employees e where LOWER(e.name) LIKE LOWER(?)";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            // Use "%" for partial matching
            stmt.setString(1, "%" + name + "%");

            ResultSet rs = stmt.executeQuery();

            // Check if any result exists
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("eid"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Age: " + rs.getInt("age"));
                System.out.println("Salary: " + rs.getDouble("salary"));
                System.out.println("Designation: " + rs.getString("designation"));
                System.out.println("Department: " + rs.getString("department"));
                System.out.println("----------------------------");
            }
        } catch (SQLException e) {
            System.out.println(e);

        } 

    }

    public static void searchEmployeeByAge(int age) {
        String query = "select * from employees e where e.age = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            // Use "%" for partial matching
            stmt.setInt(1, age);

            ResultSet rs = stmt.executeQuery();

            // Check if any result exists
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("eid"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Age: " + rs.getInt("age"));
                System.out.println("Salary: " + rs.getDouble("salary"));
                System.out.println("Designation: " + rs.getString("designation"));
                System.out.println("Department: " + rs.getString("department"));
                System.out.println("----------------------------");
            }
        } catch (SQLException e) {
            System.out.println(e);

        }
    }

    public static void searchEmployeeByDesignation(String designation) {
        String query = "select * from employees e where LOWER(e.designation) = LOWER(?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            // Use "%" for partial matching
            stmt.setString(1, designation);

            ResultSet rs = stmt.executeQuery();

            // Check if any result exists
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("eid"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Age: " + rs.getInt("age"));
                System.out.println("Salary: " + rs.getDouble("salary"));
                System.out.println("Designation: " + rs.getString("designation"));
                System.out.println("Department: " + rs.getString("department"));
                System.out.println("----------------------------");
            }
        } catch (SQLException e) {
            System.out.println(e);

        }

    }

    public static void searchEmployeeByDepartment(String department) {
        String query = "select * from employees e where LOWER(e.department) = LOWER(?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            // Use "%" for partial matching
            stmt.setString(1, department);

            ResultSet rs = stmt.executeQuery();

            // Check if any result exists
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("eid"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Age: " + rs.getInt("age"));
                System.out.println("Salary: " + rs.getDouble("salary"));
                System.out.println("Designation: " + rs.getString("designation"));
                System.out.println("Department: " + rs.getString("department"));
                System.out.println("----------------------------");
            }
        } catch (SQLException e) {
            System.out.println(e);

        } 

    }

}

class EmployeeManager {
    public static void addNewEmployee(String choice, BufferedReader br) {
        try {
            System.out.println("Enter Details of Employee");
            System.out.println("Enter Name: ");
            String name = br.readLine();
            String designation = choice;
            String department = null;
            switch (designation) {
                case "Tester":
                    department = "Quality Assurance";
                    break;
                case "Programmer":
                    department = "Development";
                    break;
                case "Manager":
                    department = "Management";
                    break;
                case "Others":
                    System.out.println("Enter the Designation: ");
                    designation = br.readLine();
                    System.out.println("Enter the Department: ");
                    department = br.readLine();
                    break;
            }
            int age = AgeReader.ageValidator(20, 60, br);
            double salary = SalaryReader.salaryValidator(10000000, br);
            DatabaseOperations.addEmployee(name, age, salary, designation, department);

        } catch (IOException e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }

    public static void giveAppraisal(BufferedReader br) {
        try {
            System.out.println("Enter the Employee ID: ");
            int employeeIdInput = Integer.parseInt(br.readLine());
            boolean result = DatabaseOperations.searchEmployeeById(employeeIdInput);
            if (result) {
                System.out.println("Please enter the amount to be Hiked(Enter C to Cancel): ");
                double amountToBeHiked;
                String confirmationString = br.readLine();
                if (confirmationString.equalsIgnoreCase("c")) {
                    System.out.println("Appraisal Operation Cancelled");
                    return;
                } else {
                    amountToBeHiked = Double.parseDouble(confirmationString);
                }
                DatabaseOperations.appraisal(employeeIdInput, amountToBeHiked);
            } else {
                System.out.println("No employee found with ID " + employeeIdInput + ". Appraisal failed.");
            }

        } catch (IOException e) {
            // General SQL error handling
            System.out.println("Error during appraisal: " + e.getMessage());
        }
    }

    public static void removeEmployee(BufferedReader br) {
        try {
            System.out.println("Enter the Employee ID: ");
            int employeeIdInput = Integer.parseInt(br.readLine());
            boolean result = DatabaseOperations.searchEmployeeById(employeeIdInput);
            if (result) {
                System.out.println("Are you sure you want to remove this employee? (Y/N)");
                String confirmationString = br.readLine();
                if (confirmationString.equalsIgnoreCase("y")) {
                    System.out.println("Removing Employee");
                    boolean res = DatabaseOperations.removeEmployee(employeeIdInput);
                    if (res)
                        System.out.println("Employee removed successfully");
                } else {
                    System.out.println("Operation Cancelled");
                    return;
                }
            } else {
                System.out.println("Employee with Id :" + employeeIdInput + " Not Found");
            }

        } catch (Exception e) {
            System.out.println(e);

        }
    }

}

class EmployeeSearch {
    public static void searchEmployeeByName(BufferedReader br) {
        try {
            System.out.println("Enter the Name: ");
            String name = br.readLine();
            DatabaseOperations.searchEmployeeByName(name);
        } catch (IOException e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }

    public static void searchEmployeeById(BufferedReader br) {
        try {
            System.out.println("Enter the Id: ");
            int eid = Integer.parseInt(br.readLine());
            DatabaseOperations.searchEmployeeById(eid);
        } catch (IOException e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }

    public static void searchEmployeeByAge(BufferedReader br) {
        try {
            System.out.println("Enter the Age: ");
            int age = Integer.parseInt(br.readLine());
            DatabaseOperations.searchEmployeeByAge(age);
        } catch (IOException e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }

    public static void searchEmployeeByDesignation(BufferedReader br) {
        try {
            System.out.println("Enter the Designation: ");
            String desig = br.readLine();
            DatabaseOperations.searchEmployeeByDesignation(desig);
        } catch (IOException e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }

    public static void searchEmployeeByDepartment(BufferedReader br) {
        try {
            System.out.println("Enter the Department: ");
            String dept = br.readLine();
            DatabaseOperations.searchEmployeeByDepartment(dept);
        } catch (IOException e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }

}

public class EmpManagementApp {

    public static void main(String[] args) {
        // Create BufferedReader outside of the loop
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int mainMenuChoice = 0, employeeMenuChoice = 0, sortingInput = 0;

        System.out.println("Welcome to Employee Management App");
        do {
            System.out.println("-------------------------------------");
            System.out.println("MAIN MENU");
            System.out.println("1. Add Employee");
            System.out.println("2. Display");
            System.out.println("3. Appraisal");
            System.out.println("4. Remove Employee");
            System.out.println("5. Search Employee");
            System.out.println("6. Exit");
            System.out.println("-------------------------------------");
            mainMenuChoice = Menu.validateChoice(6, br);
            switch (mainMenuChoice) {
                case 1:
                    System.out.println("Creating a new Employee ");
                    do {

                        System.out.println("---------------------------------------------");
                        System.out.println("1. Create Tester");
                        System.out.println("2. Create Programmer");
                        System.out.println("3. Create Manager");
                        System.out.println("4. Others");
                        System.out.println("5. Back");
                        System.out.println("--------------------------------------------");

                        employeeMenuChoice = Menu.validateChoice(5, br);
                        if (employeeMenuChoice == 1) {
                            System.out.println("Adding Details of Tester");
                            EmployeeManager.addNewEmployee("Tester", br);

                        } else if (employeeMenuChoice == 2) {
                            System.out.println("Adding Details of Programmer");
                            EmployeeManager.addNewEmployee("Programmer", br);

                        } else if (employeeMenuChoice == 3) {
                            System.out.println("Adding Details of Manager");
                            EmployeeManager.addNewEmployee("Manager", br);

                        } else if (employeeMenuChoice == 4) {
                            System.out.println("Adding Details of Employee");
                            EmployeeManager.addNewEmployee("Others", br);

                        }

                    } while (employeeMenuChoice != 5);
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
                        System.out.println("6. Back");
                        System.out.println("--------------------------------------------");

                        sortingInput = Menu.validateChoice(6, br);

                        switch (sortingInput) {
                            case 1:
                                System.out.println("Sorting by Name");
                                DatabaseOperations.displayEmployees("name");
                                break;

                            case 2:
                                System.out.println("Sorting by ID");
                                DatabaseOperations.displayEmployees("eid");

                                break;
                            case 3:
                                System.out.println("Sorting by Designation");
                                DatabaseOperations.displayEmployees("designation");

                                break;
                            case 4:
                                System.out.println("Sorting by Salary");
                                DatabaseOperations.displayEmployees("salary");

                                break;
                            case 5:
                                System.out.println("Sorting by Age");
                                DatabaseOperations.displayEmployees("age");
                                break;
                        }
                    } while (sortingInput != 6);
                    break;
                case 3:
                    System.out.println("Appraisal of Employee ");
                    EmployeeManager.giveAppraisal(br);
                    break;
                case 4:
                    System.out.println("Removing a Employee ");
                    EmployeeManager.removeEmployee(br);
                    break;
                case 5:
                    System.out.println("How do you want to Search:");
                    do {

                        System.out.println("---------------------------------------------");
                        System.out.println("1. Using ID");
                        System.out.println("2. Using Name");
                        System.out.println("3. Using Age");
                        System.out.println("4. Using Designation");
                        System.out.println("5. Using Department");
                        System.out.println("6. Back");
                        System.out.println("--------------------------------------------");

                        sortingInput = Menu.validateChoice(6, br);

                        switch (sortingInput) {
                            case 1:
                                System.out.println("Searching by ID");
                                EmployeeSearch.searchEmployeeById(br);
                                break;

                            case 2:
                                System.out.println("Searching by Name");
                                EmployeeSearch.searchEmployeeByName(br);

                                break;
                            case 3:
                                System.out.println("Searching by Age");
                                EmployeeSearch.searchEmployeeByAge(br);

                                break;
                            case 4:
                                System.out.println("Searching by Designation");
                                EmployeeSearch.searchEmployeeByDesignation(br);

                                break;
                            case 5:
                                System.out.println("Searching by Department");
                                EmployeeSearch.searchEmployeeByDepartment(br);

                                break;
                        }
                    } while (sortingInput != 6);
                    break;
            }

        } while (mainMenuChoice != 6);
    }

}

class Menu {
    private static int maxChoice;

    public static int validateChoice(int max, BufferedReader br) {
        maxChoice = max;
        while (true) {
            System.out.print("Enter Choice:- ");
            try {
                int choice = Integer.parseInt(br.readLine());
                if (choice < 1 || choice > maxChoice) {
                    throw new InvalidChoiceException();
                } else {
                    return choice;

                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a numeric value only ");
            } catch (InvalidChoiceException e) {
                e.displayMessage(maxChoice);
            } catch (IOException e) {
                System.out.println(e);
            }
        }

    }

}

class AgeReader {
    // Handler to Read and Validate Age
    public static int ageValidator(int lowerLimit, int upperLimit, BufferedReader br) {
        while (true) {
            try {
                System.out.println("Please Enter your age: ");
                int ageInput = Integer.parseInt(br.readLine());
                if (ageInput > upperLimit || ageInput < lowerLimit) {
                    throw new InvalidAgeInputException("Please enter age between " + lowerLimit + " and " + upperLimit);
                }
                return ageInput;
            } catch (NumberFormatException e) {
                System.out.println("Please enter numeric value");
                // sc.nextLine();
            } catch (InvalidAgeInputException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}

class SalaryReader {
    public static double salaryValidator(double maxSalary, BufferedReader br) {
        while (true) {
            try {
                System.out.println("Please Enter the salary: ");
                double salary = Double.parseDouble(br.readLine());
                // Check salary is not string

                if (salary > maxSalary || salary < 0) {
                    throw new InvalidSalaryInputException("Please enter a valid salary");
                }
                return salary;

            } catch (InputMismatchException e) {
                System.out.println("Please enter numeric value");
                // sc.nextLine();
            } catch (IOException e) {
                // TODO: handle exception
                System.out.println(e);
            } catch (InvalidSalaryInputException e) {
                System.out.println("Please enter a Valid Salary");
            }

        }

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

class InvalidSalaryInputException extends RuntimeException {
    InvalidSalaryInputException() {
        super();
    }

    InvalidSalaryInputException(String msg) {
        super(msg);
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
