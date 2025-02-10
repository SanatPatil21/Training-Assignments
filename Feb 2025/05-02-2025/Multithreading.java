class A
{
	public void print1to10()
	{
		for(int i=1; i<=10; i++)
		{
			System.out.println("Range : "+i);
		}
	}
}
class B
{
	public void evenTill50()
	{
		for(int i=0; i<=50; i+=2)
		{
			System.out.println("Even : "+i);
		}
	}
}
class C
{
	public void fibonacciFrom1to1000()
	{
        int a=0,b=1,c=0;
        do{
            c=a+b;
            if(c<=1000)
            System.out.println("Fibonacci: "+c);
            a=b;
            b=c;

        }while(c<=1000);
	}
}
class Multithreading
{
	public static void main(String args[])
	{
        
        A a1= new A();
        B b1= new B();
        C c1= new C();


        //Using anonymous sub class to override run method
        //Will create anonymous object of Runnable Interface
        Thread printRange = new Thread(new Runnable() {
            public void run(){
                new A().print1to10();
            }
        });

        //Will create anonymous object of Thread Interface
        Thread printRange3 = new Thread(){
            public void run(){
                a1.print1to10();
            }
        };
            

        //Using the ::(method reference) to do the same without explicitly overiding the run method
        Thread printRange2 = new Thread(a1::print1to10);
        Thread evenNumbers2 = new Thread(b1::evenTill50);
        Thread fibonacciThread2 = new Thread(c1::fibonacciFrom1to1000);


        
        Thread evenNumbers = new Thread(new Runnable() {
            public void run(){
                b1.evenTill50(); 
            }
        });

        Thread fibonacciThread = new Thread(new Runnable() {
            public void run(){
                c1.fibonacciFrom1to1000();
            }
        });

        // printRange.start();
        // printRange2.start();
        // printRange3.start();
        evenNumbers.start();
        fibonacciThread.start();

        //SHORTEST POSSIBLE SOLUTION
        new Thread(new A()::print1to10).start();

        new Thread(new B()::evenTill50).start();

        new Thread(new C()::fibonacciFrom1to1000).start();

            
	}
}
 