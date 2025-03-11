package com.example.demo.repos.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Student;


@Repository
public interface StudentPostgresRepository extends JpaRepository<Student, Integer> {

}
