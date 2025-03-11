package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.models.Student;
import com.example.demo.models.Teacher;
import com.example.demo.repos.student.StudentDao;
import com.example.demo.repos.teacher.TeacherDao;

@Service
public class StudentService {
	@Autowired
	private StudentDao stuDao;
	
	
	@Autowired
	private TeacherDao teaDao;
	

	public List<Student> getStudentList() {
		
        return stuDao.findAll();
    }
	
	public String addStudent(Student s) {	
		Student st = stuDao.findByRollNo(s.getRollNo());
		if(st.getRollNo()==s.getRollNo())
			return "Student with Same Roll No Exists";
		else {
			stuDao.save(s);
			return "Student Record Added Successfully";
		}
	}
	
	public Student getStudentByRollNo( int rollNo){
		return stuDao.findByRollNo(rollNo);
	}
	
	public List<Student> getStudentBySchool(String school){
		return stuDao.findBySchool(school);
		
	}
	
	public List<Student> getResults(String pass){
		if(pass.equalsIgnoreCase("true")) {
			return stuDao.findByPercentageGreaterThan(40);
			
		}else {
			return stuDao.findByPercentageLessThan(40);
			
		}
	}
	
	public long getCountOfStudents(int standard) {
		
		return stuDao.countByStandard(standard);
	}
	
	public long getStrengthOfSchool(String school) {
		return stuDao.countBySchool(school);
	}
	
	public List<Student> getToppers(){
		return stuDao.findTop5ByOrderByPercentageDesc();
		
	}
	
	public List<Student> getToppersByStandard(int standard){
		return stuDao.findTop5ByStandardOrderByPercentageDesc(standard);
		
	}
	
	public void addDummyRecords() {
		List<Student> students = List.of(
	            new Student(1111,1, "Sanat", 7, "SHBHS", 82),
	            new Student(2222,2, "Aarav", 1, "DPS Delhi", 85),
	            new Student(3333,3, "Anaya", 2, "St. Xavier's, Mumbai", 78),
	            new Student(4444,4, "Rahul", 3, "SHBHS", 92),
	            new Student(5555,5, "Ishika", 4, "DAV School", 36),
	            new Student(6666,6, "Rohan", 5, "National Public", 74),
	            new Student(7777,7, "Priya", 6, "Bombay Scottish", 88),
	            new Student(8888,8, "Aditya", 7, "Loyola School", 29), 
	            new Student(9999,9, "Sneha", 8, "Springdale School", 45),
	            new Student(1010,10, "Kunal", 9, "Ryan International", 67),
	            new Student(1111,11, "Neha", 10, "SHBHS", 82),
	            new Student(1212,12, "Aryan", 1, "Jamnabai Narsee", 33), 
	            new Student(1313,13, "Meera", 2, "St. Mary's, Mumbai", 76),
	            new Student(1414,14, "Vikas", 3, "SHBHS", 54),
	            new Student(1515,15, "Aditi", 4, "Bombay Scottish", 38), 
	            new Student(1616,16, "Sahil", 5, "National Public", 89),
	            new Student(1717,17, "Tanya", 6, "Jamnabai Narsee", 91),
	            new Student(1818,18, "Vivek", 7, "St. Xavier's, Mumbai", 40),
	            new Student(1919,19, "Riya", 8, "Springdale School", 35), 
	            new Student(2020,20, "Aman", 9, "Ryan International", 50),
	            new Student(3030,21, "Simran", 10, "St. Mary's, Mumbai", 94)
	        );
	    stuDao.saveAll(students);
	}
	
	public void getTeacher(int regno) {
//		stuDao.findByRegNo(int regno);
		
		
	}


	
}
