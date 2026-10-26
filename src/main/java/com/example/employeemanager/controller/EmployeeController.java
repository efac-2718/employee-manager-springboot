package com.example.employeemanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.example.employeemanager.model.Employee;

import jakarta.validation.Valid;


@Controller 
public class EmployeeController {
    //GET http://localhost:8080 --> send the browserto the main page
    @GetMapping("/")
    public String home() {
        return "redirect:/employees";
    }
    
    //GET /employees --> show the form and the table (Read)
    @GetMapping("/employees")
    public String list(Model model) {
        return "employees";
    }

    //POST /employees --> save a new employee (Create)
    @PostMapping("/employees")
    public String create(@Valid @ModelAttribute Employee employee, BindingResult result, Model model) {
        return "redirect:/employees";
    }

    //GET /employees/{id}/edit --> show th edit form (Update, part 1)
    @GetMapping("/employees/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        return "edit-employee";
    }
    
    //POST /employees/{id}/edit --> save the changes (Update, part 2)
    @PostMapping("/employees/{id}/update")
    public String update(@PathVariable Long id, @Valid @ModelAttribute Employee employee, BindingResult result) {
        return "redirect:/employees";
    }

    //POST /employees/{id}/delete --> remove the employee (Delete)
    @PostMapping("/employees/{id}/delete")
    public String delete(@PathVariable Long id) {
        return "redirect:/employees";
    }
}
