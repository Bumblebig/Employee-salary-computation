package com.example.employee_salary.repository;

import com.example.employee_salary.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {
    // You can define custom query methods if needed
}
