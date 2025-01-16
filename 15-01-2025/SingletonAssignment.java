class A {
    private static A a1 = null;

    A() throws SingletonObjectException {

            if (this instanceof B)
                System.out.println("B instance created.....");
            else if (this instanceof A && a1 == null) {
                a1 = this;
                System.out.println("A instance created....");

            } else
                throw new SingletonObjectException();
    }

    public static A getObject() {
        try {
            if (a1 == null)
                a1 = new A();
        // return a1; 
        } catch (SingletonObjectException e) {
            e.displayMessage();
        }
        return a1;
        

    }

}

class B extends A {
    private static final B b1 = new B();

    private B() {

    }

    public static B getObject() {
        return b1;
    }
}

public class SingletonAssignment {
    public static void main(String args[]) {

            A a1 = A.getObject();
            System.out.println(a1);
            B b1 = B.getObject();
            System.out.println(b1);


            B b2 = B.getObject();
            System.out.println(b2);
            
            A a2 = A.getObject();
            System.out.println(a2);


    }
}

class SingletonObjectException extends RuntimeException {
    SingletonObjectException() {
        super();
    }

    SingletonObjectException(String msg) {
        super(msg);
    }

    public void displayMessage() {
        System.out.println("Only Single object allowed");
    }
}