package com.rest.api.employee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rest.api.employee.exception.BadRequestException;
import com.rest.api.employee.exception.ResourceNotFoundException;
import com.rest.api.employee.model.Employee;
import com.rest.api.employee.model.TaxDeductionResponse;
import com.rest.api.employee.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    
 
    @PostMapping("/employees")
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) throws BadRequestException {
  
        return employeeService.createEmployee(employee);
    }
    
    @GetMapping("/employees/{employeeId}/tax-deductions")
    public ResponseEntity<TaxDeductionResponse> getTaxDeductions(@PathVariable(value = "employeeId") Long employeeId) throws ResourceNotFoundException {
    	 return employeeService.getEmployeeTax(employeeId);
    	
    }
}