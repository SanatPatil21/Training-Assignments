package com.example.demo.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@GetMapping("/students")
	public List<Student> getAllStudents(){
		return studentService.getStudentList();
	}
	
	@GetMapping("/students/{rollNo}")
	public List<Student> getStudentsByRollNo(@PathVariable int rollNo){
		return studentService.getStudentList().stream().filter(student -> student.getRollNo()==rollNo).collect(Collectors.toList());
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
		if(pass.equalsIgnoreCase("true")) {
			return studentService.getStudentList()
					.stream()
					.filter(student -> student.getPercentage()>40)
					.collect(Collectors.toList());
			
		}else {
			return studentService.getStudentList()
					.stream()
					.filter(student -> student.getPercentage()<40)
					.collect(Collectors.toList());
			
		}
		
	}
	
	@GetMapping("/students/{standard}/count")
	public long getCountOfStudents(@PathVariable int standard) {
		return studentService.getStudentList()
				.stream()
				.filter(student -> student.getStandard()==standard)
				.count();
		
	}
	
	@GetMapping("/students/strength")
	public long getStrengthOfSchool(@RequestParam("school")String school) {
		return studentService.getStudentList()
				.stream()
				.filter(student -> student.getSchool().equalsIgnoreCase(school))
				.count();
		
	}
	
	@GetMapping("/students/toppers")
	public List<Student> getToppers(){
		return studentService.getStudentList()
				.stream()
				.sorted(Comparator.comparingInt(Student::getPercentage).reversed())
				.limit(5)
				.collect(Collectors.toList());
	}
	
	
	//
	@GetMapping("/students/toppers/{standard}")
	public List<Student> getToppersByStandard(@PathVariable int standard){
		return studentService.getStudentList()
				.stream()
				.filter(student->student.getStandard()==standard)
				.sorted(Comparator.comparingInt(Student::getPercentage).reversed())
				.collect(Collectors.toList());
	}
	
	
	

}
