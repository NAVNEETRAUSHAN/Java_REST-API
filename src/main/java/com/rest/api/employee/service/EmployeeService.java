package com.rest.api.employee.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.rest.api.employee.exception.BadRequestException;
import com.rest.api.employee.model.Employee;
import com.rest.api.employee.repository.EmployeeRepository;

public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	public ResponseEntity<Employee> createEmployee(Employee employee) throws BadRequestException {
		
		long employeeId = employee.getEmployeeId();
		Optional<Employee> emp = employeeRepository.findById(employeeId);
		            
		if(emp.isPresent())
			throw new BadRequestException("Employee found for this id : " + employeeId);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(employeeRepository.save(employee));

	}

}
