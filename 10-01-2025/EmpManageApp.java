package emp.assignment;

import java.util.Scanner;

abstract class Emp {
    String name;
    int age;
    float salary;
    String designation;
    int empid;

    static int countEmp = 0; 
    static int[] empIds = new int[100]; // empolyee IDs ka array

    Emp(float salary, String designation) {
        Scanner sc = new Scanner(System.in);
        boolean uniqueId = false;
        while (!uniqueId) {
            System.out.println("Enter the Employee Id: ");
            empid = sc.nextInt();
            

            uniqueId = true;
            for (int i = 0; i < countEmp; i++) {
                if (empIds[i] == empid) {
                    uniqueId = false;
                    System.out.println("Employee ID already exists. Please enter a unique ID.");
                    break;
                }
            }
        }

        empIds[countEmp] = empid;

        System.out.println("Enter your name");
        name = sc.next();
        System.out.println("Enter your age");
        age = sc.nextInt();
        this.salary = salary;
        this.designation = designation;
        countEmp += 1;
    }

    final public void display() {
        System.out.println("Employee Id: " + empid);
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Salary: " + salary);
        System.out.println("Designation: " + designation);
        System.out.println();
    }

    public abstract void raiseSalary();
}

// Subclass for Clerk
final class Clerk extends Emp {
    Clerk() {
        super(20000, "Clerk");
    }

    public void raiseSalary() {
        salary += 2000;
    }
}

// Subclass for Programmer
final class Programmer extends Emp {
    Programmer() {
        super(30000, "Programmer");
    }

    public void raiseSalary() {
        salary += 5000;
    }
}

// Subclass for Manager
final class Manager extends Emp {
    Manager() {
        super(100000, "Manager");
    }

    public void raiseSalary() {
        salary += 15000;
    }
}

public class EmpManageApp {
    static Emp emp[] = new Emp[100];  
    public static void main(String[] args) {
        int ch1 = 0, ch2 = 0;
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("-------------------------------------");
            System.out.println("1. Create Employee");
            System.out.println("2. Display");
            System.out.println("3. Raise Salary");
            System.out.println("4. Remove Employee");
            System.out.println("5. Exit");
            System.out.println("-------------------------------------");
            System.out.print("Enter your choice: ");
            ch1 = sc.nextInt();

            switch (ch1) {
                case 1:
                    do {
                        System.out.println("---------------------------------------------");
                        System.out.println("1. Create Clerk");
                        System.out.println("2. Create Programmer");
                        System.out.println("3. Create Manager");
                        System.out.println("4. Back");
                        System.out.println("--------------------------------------------");
                        System.out.print("Enter your choice: ");
                        ch2 = sc.nextInt();
                        switch (ch2) {
                            case 1:
                                emp[Emp.countEmp] = new Clerk();
                                break;
                            case 2:
                                emp[Emp.countEmp] = new Programmer();
                                break;
                            case 3:
                                emp[Emp.countEmp] = new Manager();
                                break;
                        }
                    } while (ch2 != 4);
                    break;

                case 2:
                    if (Emp.countEmp == 0) {
                        System.out.println("No Employee Present to Display");
                    } else {
                        for (int i = 0; i < Emp.countEmp; i++) {
                            if (emp[i] != null) {  
                                emp[i].display();
                            }
                        }
                    }
                    break;

                case 3:
                    if (Emp.countEmp == 0) {
                        System.out.println("No Employee Present to Raise Salary");
                    } else {
                        for (int i = 0; i < Emp.countEmp; i++) {
                            if (emp[i] != null) {  
                                emp[i].raiseSalary();
                            }
                        }
                    }
                    break;

                    case 4:
                    if (Emp.countEmp == 0) {
                        System.out.println("No Employee Present to Remove");
                    } else {
                        System.out.print("Enter Employee ID to Remove: ");
                        int removeId = sc.nextInt();
                        boolean found = false;  

                        // Search for the employee to remove
                        for (int i = 0; i < Emp.countEmp; i++) {
                            if (emp[i] != null && emp[i].empid == removeId) {
                                // Display employee details before deletion for confirmation
                                System.out.println("Employee details:");
                                emp[i].display();

                                System.out.print("Are you sure you want to delete this employee? (Y/N): ");
                                char confirmation = sc.next().charAt(0);

                                // Check user confirmation
                                if (confirmation == 'Y' || confirmation == 'y') {
                                    emp[i] = null;  // Mark the employee as removed by setting it to null
                                    System.out.println("Employee with ID " + removeId + " has been removed.");
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
                    }
                    break;
            }
        } while (ch1 != 5);

        sc.close();
        System.out.println("Total Employees Present in the Company: " + Emp.countEmp);
    }
}
