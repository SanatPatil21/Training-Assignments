package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.models.Student;

@Service
public class StudentService {
	private List<Student> students = new ArrayList<>();
	
	public StudentService() { 
        students.add(new Student(23, "Sanat", 7, "SHBHS", 82));
        students.add(new Student(34, "Aarav", 1, "DPS Delhi", 85));
        students.add(new Student(23, "Anaya", 2, "St. Xavier's, Mumbai", 78));
        students.add(new Student(56, "Rahul", 3, "SHBHS", 92));
        students.add(new Student(25, "Ishika", 4, "DAV School", 36));
        students.add(new Student(12, "Rohan", 5, "National Public", 74));
        students.add(new Student(6, "Priya", 6, "Bombay Scottish", 88));
        students.add(new Student(45, "Aditya", 7, "Loyola School", 29)); 
        students.add(new Student(32, "Sneha", 8, "Springdale School", 45));
        students.add(new Student(12, "Kunal", 9, "Ryan International", 67));
        students.add(new Student(10, "Neha", 10, "SHBHS", 82));

        students.add(new Student(12, "Aryan", 1, "Jamnabai Narsee", 33)); 
        students.add(new Student(56, "Meera", 2, "St. Mary's, Mumbai", 76));
        students.add(new Student(34, "Vikas", 3, "SHBHS", 54));
        students.add(new Student(23, "Aditi", 4, "Bombay Scottish", 38)); 
        students.add(new Student(5, "Sahil", 5, "National Public", 89));
        students.add(new Student(34, "Tanya", 6, "Jamnabai Narsee", 91));
        students.add(new Student(44, "Vivek", 7, "St. Xavier's, Mumbai", 40));
        students.add(new Student(28, "Riya", 8, "Springdale School", 35)); 
        students.add(new Student(19, "Aman", 9, "Ryan International", 50));
        students.add(new Student(10, "Simran", 10, "St. Mary's, Mumbai", 94));
    }
	
	public List<Student> getStudentList() {
        return students;
    }

	
}
