package com.example.demo.repos.h2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Student;
@Repository
public interface StudentH2Repository extends JpaRepository<Student, Integer> {

}
