package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Employee;
import com.example.demo.repos.EmployeeDao;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeDao empdao;
	
	public void addEmployeeToDB(Employee e) {
		empdao.save(e);
		
	}
	
	public List<Employee> getAllEmployees() {
		return empdao.findAll();
	}
	
	public Optional<Employee> getEmployeeById(Integer id) {
		return empdao.findById(id);
	}

}
