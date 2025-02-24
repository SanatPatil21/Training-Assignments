package com.example.demo.models;

import jakarta.persistence.*;

@Entity
@Table(name="students")
public class Student {
	@Id
	@Column(unique = true,nullable =false)
	private int regNo;
	private int rollNo;
	private String name;
	private int standard;
	private String school;
	private int percentage;
	
	@ManyToOne
    @JoinColumn(name = "standard", referencedColumnName = "standard", insertable = false, updatable = false)
    private Teacher classTeacher;

	
	
	public int getRegNo() {
		return regNo;
	}



	public void setRegNo(int regNo) {
		this.regNo = regNo;
	}



	public Teacher getClassTeacher() {
		return classTeacher;
	}



	public void setClassTeacher(Teacher classTeacher) {
		this.classTeacher = classTeacher;
	}



	public Student() {}
	
	

	public Student(int regNo,int rollNo, String name, int standard, String school, int percentage) {
        this.regNo=regNo;
		this.rollNo = rollNo;
        this.name = name;
        this.standard = standard;
        this.school = school;
        this.percentage = percentage;
    }



	public int getRollNo() {
		return rollNo;
	}
	public void setRollNo(int rollNo) {
		this.rollNo = rollNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStandard() {
		return standard;
	}
	public void setStandard(int standard) {
		this.standard = standard;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public int getPercentage() {
		return percentage;
	}
	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}
	
	
	
	
	
	

}
