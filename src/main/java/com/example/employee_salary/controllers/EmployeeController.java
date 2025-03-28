package com.example.employee_salary.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.example.employee_salary.model.Employee;
import com.example.employee_salary.service.EmployeeService;
import com.example.employee_salary.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    // Display employee list
    @GetMapping
    public String getAllEmployees(Model model) {
        List<Employee> employees = employeeRepository.findAll();
        model.addAttribute("employees", employees);
        return "employee-list";
    }

    // Show form to add employee
    @GetMapping("/add")
    public String showAddEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "add-employee";
    }

    // Add employee (form submission)
    @PostMapping("/add")
    public String addEmployee(@ModelAttribute Employee employee) {
        employeeRepository.save(employee);
        return "redirect:/employees";
    }

    // Show compute salary form
    @GetMapping("/{id}/compute-salary")
    public String showComputeSalaryForm(@PathVariable String id, Model model) {
        Optional<Employee> employee = employeeRepository.findById(id);

        if (employee.isPresent()) {
            model.addAttribute("employee", employee.get());
            return "compute-salary";
        } else {
            return "redirect:/employees";
        }
    }

    // Compute salary
    @PostMapping("/{id}/compute-salary")
    public String computeSalary(@PathVariable String id,
            @RequestParam int overtimeHours,
            Model model) {
        try {
            // Compute salary and get detailed breakdown
            double totalSalary = employeeService.computeSalary(id, overtimeHours);
            Optional<Employee> employee = employeeRepository.findById(id);

            if (employee.isPresent()) {
                Employee emp = employee.get();
                double baseSalary = emp.getBaseSalary();
                double overtimeAmount = overtimeHours * emp.getOvertimeRate();

                model.addAttribute("employee", emp);
                model.addAttribute("computedSalary", totalSalary);
                model.addAttribute("baseSalary", baseSalary);
                model.addAttribute("overtimeHours", overtimeHours);
                model.addAttribute("overtimeAmount", overtimeAmount);

                return "compute-salary";
            }
        } catch (RuntimeException e) {
            // Handle employee not found or other computation errors
            return "redirect:/employees";
        }

        return "redirect:/employees";
    }
}

// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import com.example.employee_salary.model.Employee;
// import com.example.employee_salary.service.EmployeeService;
// import com.example.employee_salary.repository.EmployeeRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;
// import java.util.Map;

// API ENDPOINTS

// @RestController
// @RequestMapping("/employees")
// public class EmployeeController {

// @Autowired
// private EmployeeRepository employeeRepository;

// @Autowired
// private EmployeeService employeeService;

// // 1. Add a new employee
// @PostMapping
// public Employee addEmployee(@RequestBody Employee employee) {
// return employeeRepository.save(employee);
// }

// // 2. Get all employees
// @GetMapping
// public List<Employee> getAllEmployees() {
// return employeeRepository.findAll();
// }

// // 3. Compute salary for an employee
// @PostMapping("/{id}/compute-salary")
// public Map<String, Object> computeSalary(@PathVariable String id,
// @RequestParam int overtimeHours) {
// double totalSalary = employeeService.computeSalary(id, overtimeHours);

// return Map.of(
// "employeeId", id,
// "computedSalary", totalSalary);
// }
// }

// UI TEMPLATING
