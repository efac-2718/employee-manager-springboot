package com.example.employeemanager.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.employeemanager.model.Employee;
import com.example.employeemanager.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class EmployeeService {

    private final EmployeeRepository repository;

    public List<Employee> findAll() {
        return repository.findAll();
    }

    public Employee findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No employee with id " + id));
    }

    public Employee create(Employee employee) {
        if (repository.existsByEmail(employee.getEmail())) {
            throw new IllegalArgumentException("This email is already registered");
        }
        return repository.save(employee);
    }

    public Employee update (Long id, Employee changes) {
        Employee employee = findById(id);
        if (repository.existsByEmailAndIdNot(changes.getEmail(), id)) {
            throw new IllegalArgumentException("This email is already registered");
        }
        employee.setName(changes.getName());
        employee.setEmail(changes.getEmail());
        employee.setPhone(changes.getPhone());
        employee.setDepartment(changes.getDepartment());
        employee.setSalary(changes.getSalary());
        return repository.save(employee);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }
    
}

