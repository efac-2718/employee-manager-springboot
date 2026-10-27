package com.example.employeemanager.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.employeemanager.model.Employee;
import com.example.employeemanager.service.EmployeeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeApiController {

    private final EmployeeService employeeService;

    // GET /employees → all employees (Read)
    @GetMapping
    public List<Employee> list() {
        return employeeService.findAll();
    }

    // GET /employees/{id} → one employee (Read)
    @GetMapping("/{id}")
    public Employee get(@PathVariable Long id) {
        return employeeService.findById(id);
    }

    // POST /employees → save a new employee (Create)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(@Valid @RequestBody Employee employee) {
        employee.setId(null); // ignore any id sent in the body so this can't overwrite an existing row
        return employeeService.create(employee);
    }

    // PUT /employees/{id} → save the changes (Update)
    @PutMapping("/{id}")
    public Employee update(@PathVariable Long id, @Valid @RequestBody Employee employee) {
        return employeeService.update(id, employee);
    }

    // DELETE /employees/{id} → remove the employee (Delete)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        employeeService.delete(id);
    }

    // Validation failed (@NotBlank, @Pattern...) → 400 with one message per field
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException e) {
        Map<String, String> errors = new LinkedHashMap<>();
        e.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    // Errors from the service (duplicate email, unknown id) → 400 with the message
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadRequest(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}