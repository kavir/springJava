package com.authh.springJwt.Authentication.service;
// package com.authh.springJwt.service;


// import java.util.ArrayList;
// import java.util.List;
// import org.springframework.beans.BeanUtils;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import com.authh.springJwt.model.Employee;
// import com.authh.springJwt.model.EmployeeEntity;
// import com.authh.springJwt.repo.EmployeeRepository;

// @Service
// public class EmployeeServiceImpl implements EmployeeService{
//     @Autowired
//     private EmployeeRepository employeeRepositoryy;
//     @Override
//     public String createEmployee(Employee employee) {
//         EmployeeEntity employeeEntity = new EmployeeEntity();
//         BeanUtils.copyProperties(employee, employeeEntity);
//         employeeRepositoryy.save(employeeEntity);
//         return "Created Sucessfully";
//     }

//     @Override
//     public List<Employee> readEmployees() {
//         List<EmployeeEntity> employeeList=employeeRepositoryy.findAll();
//         List<Employee> employees = new ArrayList<>();
//         for(EmployeeEntity employeeEntity:employeeList){
//             Employee employee = new Employee();
//             employee.setName(employeeEntity.getName());
//             employee.setEmail(employeeEntity.getEmail());
//             employee.setId(employeeEntity.getId());
//             employee.setPhone(employeeEntity.getPhone());
//             // BeanUtils.copyProperties(employeeEntity, employee);
//             employees.add(employee);
//         }
//         return employees;
//     }

//     @Override
//     public boolean deleteEmployee(Long id) {
//         employeeRepositoryy.deleteById(id);
//         return true;
//     }
//     @Override
// public String updateEmployee(Long id, Employee employee) {
//     EmployeeEntity employeeEntity = employeeRepositoryy.findById(id).orElse(null);
//     if (employeeEntity != null) {
//         // Updating fields
//         employeeEntity.setName(employee.getName());
//         employeeEntity.setEmail(employee.getEmail());
//         employeeEntity.setPhone(employee.getPhone());
//         employeeRepositoryy.save(employeeEntity);  // Save the updated entity
//         return "Updated Successfully";
//     }
//     return "Employee Not Found";
// }


// }
