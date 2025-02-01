package com.crud.Rest.api.service;

import com.crud.Rest.api.model.City;
import com.crud.Rest.api.model.Department;
import com.crud.Rest.api.model.Employee;
import com.crud.Rest.api.repository.CityRepo;
import com.crud.Rest.api.repository.DepartmentRepo;
import com.crud.Rest.api.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepo _empRepo;
    private final DepartmentRepo _deptRepo;
    private final CityRepo _cityRepo;

    @Autowired
    public EmployeeService(EmployeeRepo empRepo, DepartmentRepo deptRepo, CityRepo cityRepo) {
        this._empRepo = empRepo;
        this._deptRepo = deptRepo;
        this._cityRepo = cityRepo;
    }

    /** Creates a new employee and returns it, or throws an exception if a database or unexpected error occurs. */
    public Employee createEmployee(Employee employee) {
        try {
            return _empRepo.save(employee);
        } catch (DataAccessException e) {
            System.out.println("SQL error while saving employee: {}" + e.getMessage());
            throw new RuntimeException("Database error: Unable to save employee.");
        } catch (Exception e) {
            System.out.println("Unexpected error while saving employee: {}" + e.getMessage());
            throw new RuntimeException("An unexpected error occurred while saving the employee.");
        }
    }

    /** Retrieves all employees with city and department details, or throws an exception if a database or unexpected error occurs. */
    public List<Employee> getAllEmployees() {
        try {
            return _empRepo.findAllEmployeesWithCityAndDepartment();
        } catch (DataAccessException e) {
            System.out.println("Database access error while fetching employees: " + e.getMessage());
            throw new RuntimeException("Database error: Unable to fetch employees.");
        } catch (Exception e) {
            System.out.println("Unexpected error while fetching employees: " + e.getMessage());
            throw new RuntimeException("An unexpected error occurred while fetching employees.");
        }
    }

    /** Retrieves an employee by ID, or throws an exception if a database or unexpected error occurs. */
    public Employee getEmployee(int empId) {
        try {
            return _empRepo.findById(empId).orElse(null);
        } catch (DataAccessException e) {
            System.out.println("Database access error while fetching employee: " + e.getMessage());
            throw new RuntimeException("Database error: Unable to fetch employee.");
        } catch (Exception e) {
            System.out.println("Unexpected error while fetching employee: " + e.getMessage());
            throw new RuntimeException("An unexpected error occurred while fetching employee.");
        }
    }

    /** Updates an employee's details by ID and returns the updated employee, or throws exceptions if any error occurs. */
    public Employee updateEmployee(int id, Employee emp) throws IOException {
        Employee existingEmp = _empRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));

        // Update fields if provided in request
        if (emp.getFirstName() != null) {
            existingEmp.setFirstName(emp.getFirstName());
        }
        if (emp.getLastName() != null) {
            existingEmp.setLastName(emp.getLastName());
        }
        if (emp.getJob_Title() != null) {
            existingEmp.setJob_Title(emp.getJob_Title());
        }
        if (emp.getSalary() != null && emp.getSalary() > 0) {
            existingEmp.setSalary(emp.getSalary());
        }
        if (emp.getDept() != null && emp.getDept().getDeptId() != null) {
            Department dept = _deptRepo.findById(emp.getDept().getDeptId())
                    .orElseThrow(() -> new RuntimeException("Department not found"));
            existingEmp.setDept(dept);
        }
        if (emp.getCity() != null && emp.getCity().getCityId() != null) {
            City city = _cityRepo.findById(emp.getCity().getCityId())
                    .orElseThrow(() -> new RuntimeException("City not found"));
            existingEmp.setCity(city);
        }

        // Save updated employee
        try {
            return _empRepo.save(existingEmp);
        } catch (DataAccessException e) {
            System.out.println("Database access error while updating employee: " + e.getMessage());
            throw new RuntimeException("Database error: Unable to update employee.");
        } catch (Exception e) {
            System.out.println("Unexpected error while updating employee: " + e.getMessage());
            throw new RuntimeException("An unexpected error occurred while updating employee.");
        }
    }

    /** Deletes an employee by ID, or throws an exception if a database or unexpected error occurs. */
    public void deleteEmployee(int empId) {
        try {
            _empRepo.deleteById(empId);
            System.out.println("Employee deleted successfully with ID: " + empId);
        } catch (DataAccessException e) {
            System.out.println("Database access error while deleting employee: " + e.getMessage());
            throw new RuntimeException("Database error: Unable to delete employee.");
        } catch (Exception e) {
            System.out.println("Unexpected error while deleting employee: " + e.getMessage());
            throw new RuntimeException("An unexpected error occurred while deleting employee.");
        }
    }

}
