	package com.example.demo.models;
	
	import java.io.BufferedReader;
	import java.io.InputStreamReader;
	
	public abstract class Employee {
		private String name;
		private int age;
		protected String designation;
	    protected float salary;
	    private int id=0;
	    private Address address;
	    
	    public Employee(float salary, String desig) {
	    	this.salary=salary;
	    	this.designation=desig;
	    	inputDetails();
	    		
	    }
	    	
		
		@Override
		public String toString() {
			return "Employee [name=" + name + ", age=" + age + ", designation=" + designation + ", salary=" + salary
					+ ", id=" + getId() + ", " + address.toString() + "]";
		}
		
		public abstract void raiseSalary();
	
	
		//Input Method
		public void inputDetails() {
	        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
	            System.out.println("Enter Employee Details");
	            System.out.print("Name: ");
	            this.name = br.readLine();
	            System.out.print("Age: ");
	            this.age = Integer.parseInt(br.readLine());
	            
	            Address addr = new Address();
	            System.out.print("ENTER ADDRESS");
	            System.out.print("ENTER City: ");
	            addr.setCity(br.readLine());
	            System.out.print("ENTER State: ");
	            addr.setState(br.readLine());
	            System.out.print("ENTER Pincode: ");
	            addr.setZip(Integer.parseInt(br.readLine()));
	            this.address=addr;
	            
	            
	        } catch (Exception e) {
	            System.out.println(e);
	        } 
	    }
		
		
		
		
	
	
		public int getId() {
			return id;
		}
	
	
		public void setId(int id) {
			this.id = id;
		}
	    
	}
