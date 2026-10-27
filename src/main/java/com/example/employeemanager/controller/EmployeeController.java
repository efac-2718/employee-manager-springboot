package com.example.employeemanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.employeemanager.model.Employee;
import com.example.employeemanager.service.EmployeeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller  
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    // GET http://localhost:8080 → send the browser to the main page
    @GetMapping("/")
    public String home() {
        return "redirect:/employees";
    }

    // GET /employees → show the form and the table (Read)
    @GetMapping("/employees")
    public String list(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("employees", employeeService.findAll());
        return "employees";
    }

    // POST /employees → save a new employee (Create)
    @PostMapping("/employees")
    public String create(@Valid @ModelAttribute Employee employee, BindingResult result,
                         Model model, RedirectAttributes redirect) {
        if (!result.hasErrors()) {
            try {
                employeeService.create(employee);
                redirect.addFlashAttribute("message", "Employee saved successfully");
                return "redirect:/employees";
            } catch (IllegalArgumentException e) {
                result.rejectValue("email", "duplicate", e.getMessage());
            }
        }
        // something was wrong: show the same page again with the error messages
        model.addAttribute("employees", employeeService.findAll());
        return "employees";
    }

    // GET /employees/{id}/edit → show the edit form (Update, part 1)
    @GetMapping("/employees/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("employee", employeeService.findById(id));
        return "edit-employee";
    }

    // POST /employees/{id}/update → save the changes (Update, part 2)
    @PostMapping("/employees/{id}/update")
    public String update(@PathVariable Long id, @Valid @ModelAttribute Employee employee,
                         BindingResult result, RedirectAttributes redirect) {
        if (!result.hasErrors()) {
            try {
                employeeService.update(id, employee);
                redirect.addFlashAttribute("message", "Employee updated successfully");
                return "redirect:/employees";
            } catch (IllegalArgumentException e) {
                result.rejectValue("email", "duplicate", e.getMessage());
            }
        }
        employee.setId(id); // the form needs the id to build its "update" URL again
        return "edit-employee";
    }

    // POST /employees/{id}/delete → remove the employee (Delete)
    @PostMapping("/employees/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirect) {
        employeeService.delete(id);
        redirect.addFlashAttribute("message", "Employee deleted");
        return "redirect:/employees";
    }
}