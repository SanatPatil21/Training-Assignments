import java.lang.reflect.Method;
import java.util.Scanner;
import java.lang.NoSuchMethodException;
import java.lang.reflect.InvocationTargetException;

class Calculator {
    public int add(int a, int b) {
        return a + b;
    }

    public int sub(int a, int b) {
        return a - b;
    }

    public int mul(int a, int b) {
        return a * b;
    }

    public int div(int a, int b) throws ArithmeticException {
        if (b == 0) {
            throw new ArithmeticException("Cannot divide by zero.");
        }
        return a / b;
    }
}

public class Calculation {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            // Handling invalid class input
            System.out.print("Enter the Classname: ");
            String className = sc.nextLine();
            Class c1 = null;
            try {
                c1 = Class.forName(className);
            } catch (ClassNotFoundException e) {
                System.out.println("Error: Class not found.");
                return;
            }

            //Creates Object
            Object obj = c1.getDeclaredConstructor().newInstance();


            //getDeclaredMethods() gets
            Method[] methods = c1.getDeclaredMethods();
            System.out.println("List of Operations:");
            for (Method m : methods) {
                System.out.println(m.getName());
            }

            System.out.print("Choice: ");
            String operation = sc.next();

            // Handling methods which are not present
            Method method = null;
            try {
                method = c1.getMethod(operation, int.class, int.class);
            } catch (NoSuchMethodException e) {
                System.out.println("Error: Method '" + operation + "' not found.");
                return;
            }

            // Taking in the input for Parameters
            int param1 = 0, param2 = 0;
            System.out.print("Enter Parameter 1: ");
            param1 = sc.nextInt();
            System.out.print("Enter Parameter 2: ");
            param2 = sc.nextInt();

            // Invoking the method and handling runtime exceptions
            try {
                int result = (int) method.invoke(obj, param1, param2);
                System.out.println("Result: " + result);
            } catch (IllegalAccessException e) {
                //Handling out of access methods
                System.out.println("Error: Method is not accessible.");
            } 
            catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
