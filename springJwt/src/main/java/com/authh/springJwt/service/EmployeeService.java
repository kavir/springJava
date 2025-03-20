package com.authh.springJwt.service;


import java.util.List;

import com.authh.springJwt.model.Employee;

public interface EmployeeService {
    String createEmployee(Employee employee);
    List<Employee> readEmployees();
    boolean deleteEmployee(Long id);
    String updateEmployee(Long id, Employee employee); 
}
