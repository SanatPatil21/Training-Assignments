package com.example.demo.models;

import com.example.demo.models.Student;

import jakarta.persistence.*;

@Entity
@Table(name = "students") 
public class Student {
    
    @Id
    private int rollNo;
    
    private String name;
    private int standard;
    private String school;
    private int percentage;

	// Getters and Setters
    public int getRollNo() { return rollNo; }
    public void setRollNo(int rollNo) { this.rollNo = rollNo; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getStandard() { return standard; }
    public void setStandard(int standard) { this.standard = standard; }

    public String getSchool() { return school; }
    public void setSchool(String school) { this.school = school; }

    public int getPercentage() { return percentage; }
    public void setPercentage(int percentage) { this.percentage = percentage; }
}
