package com.rest.api.employee.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.rest.api.employee.exception.BadRequestException;
import com.rest.api.employee.exception.ResourceNotFoundException;
import com.rest.api.employee.model.Employee;
import com.rest.api.employee.model.TaxDeductionResponse;
import com.rest.api.employee.repository.EmployeeRepository;

public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	public ResponseEntity<Employee> createEmployee(Employee employee) throws BadRequestException {

		long employeeId = employee.getEmployeeId();
		Optional<Employee> emp = employeeRepository.findById(employeeId);

		if (emp.isPresent())
			throw new BadRequestException("Employee found for this id : " + employeeId);

		return ResponseEntity.status(HttpStatus.CREATED).body(employeeRepository.save(employee));

	}

	public ResponseEntity<TaxDeductionResponse> getEmployeeTax(Long employeeId) throws ResourceNotFoundException {

		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

		double salary = employee.getSalary();
		double tax = calculateTax(salary);
		double cess = calculateCess(salary);

		TaxDeductionResponse response = new TaxDeductionResponse();
		response.setEmployeeId(employeeId);
		response.setFirstName(employee.getFirstName());
		response.setLastName(employee.getLastName());
		response.setYearlySalary(salary);
		response.setTaxAmount(tax);
		response.setCessAmount(cess);
		return ResponseEntity.ok().body(response);

	}

	private double calculateTax(double salary) {
		double tax = 0;

		if (salary > 250000 && salary <= 500000) {
			tax = (salary - 250000) * 0.05;
		} else if (salary > 500000 && salary <= 1000000) {
			tax = 12500 + (salary - 500000) * 0.10;
		} else if (salary > 1000000) {
			tax = 62500 + (salary - 1000000) * 0.20;
		}

		return tax;
	}

	private double calculateCess(double salary) {
		return salary > 2500000 ? (salary - 2500000) * 0.02 : 0;
	}

}
