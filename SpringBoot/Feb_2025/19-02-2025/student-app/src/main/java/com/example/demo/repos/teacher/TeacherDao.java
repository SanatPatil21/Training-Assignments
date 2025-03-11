package com.example.demo.repos.teacher;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Teacher;

public interface TeacherDao extends JpaRepository<Teacher, Integer>{
	

}
