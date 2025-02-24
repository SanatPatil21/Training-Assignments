package com.example.demo.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Student;
import com.example.demo.services.StudentService;

@RestController
public class StudentController {
	
	@Autowired
    private StudentService studentService;
	
	@GetMapping(path="/")
	public String getBasePath() {
		return "App is Ready";
	}
	
	//New REST Endpoints after updating Service
	@PostMapping("/add")
	public String addStudent(@RequestBody Student s) {
		return studentService.addStudent(s);
	}

	
	@GetMapping("/students")
	public List<Student> getAllStudents(){
		return studentService.getStudentList();
	}
	@GetMapping("/students/{rollNo}")
	public Student getStudentByRollNo(@PathVariable int rollNo){
		return studentService.getStudentByRollNo(rollNo);
	}
	
	@GetMapping("/students/school")
	public List<Student> getStudentBySchool(@RequestParam("name")String schoolname){
		return studentService.getStudentList()
				.stream()
				.filter(student -> student.getSchool().equalsIgnoreCase(schoolname))
				.collect(Collectors.toList());
		
	}
	
	
	@GetMapping("/students/result")
	public List<Student> getResults(@RequestParam("pass")String pass){
		
		return studentService.getResults(pass);
		
		
	}
	
	@GetMapping("/students/{standard}/count")
	public long getCountOfStudents(@PathVariable int standard) {
		return studentService.getCountOfStudents(standard);
		
	}
	
	@GetMapping("/students/strength")
	public long getStrengthOfSchool(@RequestParam("school")String school) {
		return studentService.getStrengthOfSchool(school);
		
	}
	
	
	@GetMapping("/students/toppers")
	public List<Student> getToppers(){
		return studentService.getToppers();
	}
	

	@GetMapping("/students/toppers/{standard}")
	public List<Student> getToppersByStandard(@PathVariable int standard){
		return studentService.getToppersByStandard(standard);
	}
	
	@GetMapping("/add/dummy")
	public void addData() {
		studentService.addDummyRecords();
	}
	
	/*
	@GetMapping("/students/class_teacher")
	public void getClassTeacher(@RequestParam("regno")int regno) {
		studentService.getTeacher(regno);
	}
	
	*/
	
	
	
	
	

	

	
	
	
	
	

}
