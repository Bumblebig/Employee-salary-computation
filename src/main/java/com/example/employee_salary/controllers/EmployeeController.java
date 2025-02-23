package com.example.employee_salary.controllers;

import com.example.employee_salary.model.Employee;
import com.example.employee_salary.service.EmployeeService;
import com.example.employee_salary.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    // 1. Add a new employee
    @PostMapping
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    // 2. Get all employees
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // 3. Compute salary for an employee
    @PostMapping("/{id}/compute-salary")
    public Map<String, Object> computeSalary(@PathVariable String id, @RequestParam int overtimeHours) {
        double totalSalary = employeeService.computeSalary(id, overtimeHours);

        return Map.of(
                "employeeId", id,
                "computedSalary", totalSalary);
    }
}