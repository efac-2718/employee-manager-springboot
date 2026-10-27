package com.example.employeemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.employeemanager.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, Long id);
}
