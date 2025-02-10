import java.io.*;
import java.sql.*;
import java.util.*;


import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;

//Use RowSet instead of ResultSet

class DatabaseConnection {
	private static final String url ="mongodb://localhost:27017";
	private static final String dbName = "EmpManagementApp";
	private static final String collectionName = "employees";
	private static MongoDatabase mongoDatabase = null;
	private static MongoClient mongoClient = null;
	
	static {
		mongoClient = MongoClients.create(url);
        mongoDatabase = mongoClient.getDatabase(dbName);
		System.out.println("Connection Object Created");
	}
	

    public static MongoCollection<Document> getMongoCollection() {
 
    	MongoCollection<Document> collections = mongoDatabase.getCollection(collectionName);
    	
    	return collections;
    }
    
    public static void closeConnection() {
    	mongoClient.close();
    } 
}

// Creating a sep class to handle all operations on database
class DatabaseOperations {
	
	public static void displayDetails(Document d) {
		System.out.println("Employee Id: "+d.getLong("eid"));
		System.out.println("Name: "+d.getString("name"));
		System.out.println("Age: "+d.getInteger("age"));
		System.out.println("Salary: "+d.getDouble("salary"));
		System.out.println("Designation: "+d.getString("designation"));
		System.out.println("Department: "+d.getString("department"));	
	}
 
    public static void addEmployee(String name, int age, double salary, String designation, String department){
    	try{
    		MongoCollection<Document> collections = DatabaseConnection.getMongoCollection();    	
    		
    		long count = collections.countDocuments();
    		Document doc = new Document();
    		doc.append("eid", count+1);
    		doc.append("name", name);
    		doc.append("age", age);
    		doc.append("salary", salary);
    		doc.append("designation", designation);
    		doc.append("department", department);
    		
    		collections.insertOne(doc);
    		System.out.println("Employee Added Successfully");
    		
    	}catch(Exception e) {
    		System.out.println(e);
    	}
    }   
    
    public static void displayEmployees(String orderByParam) {
        
        try {
        	MongoCollection<Document> collections = DatabaseConnection.getMongoCollection(); 
        	Bson customSorter = Sorts.ascending(orderByParam);
        	FindIterable<Document> i = collections.find().sort(customSorter);  
        	for(Document d:i) {
        		displayDetails(d);
        		System.out.println("-------------------------");
        	}

        } catch (MongoException e) {
            System.out.println(e);
        } 
    }
    

    public static void appraisal(int eid, double amountToBeHiked) {
        try {
            MongoCollection<Document> collection = DatabaseConnection.getMongoCollection();

            // Find the employee
            Document employee = collection.find(Filters.eq("eid", eid)).first();

            if (employee == null) {
                System.out.println("Employee not found!");
                return;
            }

            double olderSalary = employee.getDouble("salary"); 
            Bson update = Updates.set("salary", olderSalary + amountToBeHiked);
            
            collection.updateOne(Filters.eq("eid", eid), update);
            System.out.println("Salary updated successfully for Employee ID: " + eid);

        } catch (MongoException e) {
            System.out.println("Database error updating salary: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

    
    public static boolean searchEmployeeById(int eid) {
        try {
            MongoCollection<Document> collection = DatabaseConnection.getMongoCollection();
            Document employee = collection.find(Filters.eq("eid", eid)).first();
            displayDetails(employee);
            System.out.println();
            return employee != null;
        } catch (MongoException e) {
            System.out.println("Error searching for employee: " + e.getMessage());
        }
        return false;
    }
    
    public static void removeEmployee(int eid) {
    	try {
    		MongoCollection<Document> collection = DatabaseConnection.getMongoCollection();
    		collection.deleteOne(Filters.eq("eid", eid));
    		System.out.println("Employee with Emp Id " + eid+" sucessfully deleted");
		
    	}catch(Exception e) {
    		System.out.println(e);
    	}
    	
    }

    


    

    public static void searchEmployeeByName(String name) {
    	try {
    		MongoCollection<Document> collection = DatabaseConnection.getMongoCollection();
    		FindIterable<Document> i = collection.find(Filters.eq("name", name));
    		if(i.first() == null) {
    			System.out.println("No Employees Found");
    			return;
    		}
    		for(Document d:i) {
    			displayDetails(d);
    			System.out.println("----------------------");
    		}
    		
    	}catch(Exception e) {
    		System.out.println(e);
    	}
    }
    public static void searchEmployeeByAge(int age) {
    	try {
    		MongoCollection<Document> collection = DatabaseConnection.getMongoCollection();
    		FindIterable<Document> i = collection.find(Filters.eq("age", age));
    		for(Document d:i) {
    			displayDetails(d);
    			System.out.println("----------------------");
    		}
    		
    	}catch(Exception e) {
    		System.out.println(e);
    	}
    }
    public static void searchEmployeeByDesignation(String designation) {
    	try {
    		MongoCollection<Document> collection = DatabaseConnection.getMongoCollection();
    		FindIterable<Document> i = collection.find(Filters.eq("designation", designation));
    		for(Document d:i) {
    			displayDetails(d);
    			System.out.println("----------------------");
    		}
    		
    	}catch(Exception e) {
    		System.out.println(e);
    	}
    }
    public static void searchEmployeeByDepartment(String department) {
    	try {
    		MongoCollection<Document> collection = DatabaseConnection.getMongoCollection();
    		FindIterable<Document> i = collection.find(Filters.eq("department", department));
    		for(Document d:i) {
    			displayDetails(d);
    			System.out.println("----------------------");
    		}
    		
    	}catch(Exception e) {
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
            System.out.print("Enter the Employee ID: ");
            int employeeIdInput = Integer.parseInt(br.readLine());

            // Ensure employee exists
            boolean employeeExists = DatabaseOperations.searchEmployeeById(employeeIdInput);

            if (!employeeExists) {
                System.out.println("No employee found with ID " + employeeIdInput + ". Appraisal failed.");
                return;
            }

            System.out.print("Please enter the amount to be Hiked (Enter C to Cancel): ");
            String input = br.readLine();

            if (input.equalsIgnoreCase("c")) {
                System.out.println("Appraisal Operation Cancelled.");
                return;
            }

            try {
                double amountToBeHiked = Double.parseDouble(input);
                DatabaseOperations.appraisal(employeeIdInput, amountToBeHiked);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid numeric value for the salary hike.");
            }

        } catch (IOException e) {
            System.out.println("Error reading input: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }
    

    public static void removeEmployee(BufferedReader br) {
        try {
        	System.out.print("Enter the Employee ID to be deleted: ");
            int employeeIdInput = Integer.parseInt(br.readLine());
            boolean employeeExists = DatabaseOperations.searchEmployeeById(employeeIdInput);
            if (employeeExists) {
                System.out.println("Are you sure you want to remove this employee? (Y/N)");
                String confirmationString = br.readLine();
                if (confirmationString.equalsIgnoreCase("y")) {
                    System.out.println("Removing Employee");
                    DatabaseOperations.removeEmployee(employeeIdInput);
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


public class EmpManagement {

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
                    //EmployeeManager.removeEmployee(br);
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
        
        DatabaseConnection.closeConnection();
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
