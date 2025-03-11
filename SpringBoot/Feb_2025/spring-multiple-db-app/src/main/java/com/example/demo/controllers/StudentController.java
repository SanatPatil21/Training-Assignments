package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Student;
import com.example.demo.service.StudentService;

@RestController
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	
	
	@PostMapping("/student")
	public String insertStudentIntoTwoDb(@RequestBody Student s) {
		studentService.saveUser(s);
		return "Student Inserted";
		
		
	}

}
