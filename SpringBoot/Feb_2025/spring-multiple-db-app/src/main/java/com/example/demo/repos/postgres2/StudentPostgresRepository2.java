package com.example.demo.repos.postgres2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Student;


@Repository
public interface StudentPostgresRepository2 extends JpaRepository<Student, Integer> {

}
