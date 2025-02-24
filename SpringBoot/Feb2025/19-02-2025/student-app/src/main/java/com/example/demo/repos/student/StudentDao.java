package com.example.demo.repos.student;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Student;

@Repository
public interface StudentDao extends JpaRepository<Student, Integer>{

	Student findByRollNo(int rollNo);
	List<Student> findBySchool(String school);
	List<Student> findByPercentageGreaterThan(int percentage);
	List<Student> findByPercentageLessThan(int percentage);
	long countBySchool(String school);
	long countByStandard(int standard);
	
	List<Student> findTop5ByOrderByPercentageDesc();

	
	List<Student> findTop5ByStandardOrderByPercentageDesc(int standard);
	Student findByRegNo(int regno);
	
	
	
	
}
