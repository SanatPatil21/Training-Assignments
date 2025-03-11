package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Student;
import com.example.demo.repos.h2.StudentH2Repository;
import com.example.demo.repos.postgres.StudentPostgresRepository;
import com.example.demo.repos.postgres2.StudentPostgresRepository2;

import jakarta.transaction.Transactional;

@Service
public class StudentService {
	
	@Autowired
	private StudentH2Repository stuDaoH2;
	@Autowired
	private StudentPostgresRepository stuDaoPg;
	@Autowired
	private StudentPostgresRepository2 stuDaoPg2;
	
	@Transactional
    public void saveUser(Student s) {
		
        stuDaoH2.save(s);
        stuDaoPg.save(s);
        stuDaoPg2.save(s);
    }
	
	
	

}
