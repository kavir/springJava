package com.authh.springJwt.controller;

import org.springframework.web.bind.annotation.RestController;
import com.authh.springJwt.model.Employee;
import com.authh.springJwt.service.EmployeeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController
@RequestMapping("/api")
@CrossOrigin
// @CrossOrigin("*")
public class EmpController {
    
    @Autowired
    EmployeeService employeeService;

    @GetMapping("employees")
    public List<Employee> getAllEmployees() {
        System.out.println("siuuu___Employee:displayed");
        return employeeService.readEmployees();
    }
    @PostMapping("employees")
    public String createEmployee(@RequestBody Employee employee) {
        System.out.println("siuuu___Received Employee: " + employee);
        employeeService.createEmployee(employee);
        return "Created Employee Successfully";
    }
    @PutMapping("employees/{id}")
public String updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
    return employeeService.updateEmployee(id, employee);
}
    @DeleteMapping("employees/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        if(employeeService.deleteEmployee(id)) {
            return "Deleted Employee Successfully";
        }
        return "Not Found";
    }
}
