class Calculator
{
	public void add(int a, int b)
	{
		System.out.println("Addition : "+(a+b));
	}
	public void sub(int a, int b)
	{
		System.out.println("Subtract : "+(a-b));
		// System.out.println("Test");
	}
}
public class CalculatorJenkinsDemo
{
	public static void main(String args[])
	{
		System.out.println("Calculator Operations......");
 
		Calculator c1 = new Calculator();
		c1.add(10, 20);
		c1.sub(10, 20);
	}
}