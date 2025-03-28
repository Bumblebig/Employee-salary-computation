package com.example.employee_salary.service;

import com.example.employee_salary.model.Employee;
import com.example.employee_salary.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public double computeSalary(String employeeId, int overtimeHours) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);

        if (optionalEmployee.isEmpty()) {
            throw new RuntimeException("Employee not found");
        }

        Employee employee = optionalEmployee.get();
        double baseSalary = employee.getBaseSalary();
        double allowances = employee.getAllowances();

        // Overtime rate is typically 1.5x regular hourly rate
        double hourlyRate = baseSalary / 160; // Assuming 160 work hours per month
        double overtimePay = overtimeHours * (hourlyRate * 1.5);

        return baseSalary + allowances + overtimePay;
    }
}