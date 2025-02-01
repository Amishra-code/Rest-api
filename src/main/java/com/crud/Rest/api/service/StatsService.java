package com.crud.Rest.api.service;

import com.crud.Rest.api.model.Employee;
import com.crud.Rest.api.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatsService {
    @Autowired
    private EmployeeRepo _empRepo;

    /** Retrieves all employees details belonging to a specific city, or throws an exception if a database or unexpected error occurs. */
    public List<Employee> getAllEmployeesFromCity(int cityId) {
        try {
            return _empRepo.findByCity_CityId(cityId);
        } catch (DataAccessException e) {
            System.out.println("Database access error while fetching employees: " + e.getMessage());
            throw new RuntimeException("Database error: Unable to fetch employees.");
        } catch (Exception e) {
            System.out.println("Unexpected error while fetching employees: " + e.getMessage());
            throw new RuntimeException("An unexpected error occurred while fetching employees.");
        }
    }

    /** Retrieves all employees details belonging to a specific department, or throws an exception if a database or unexpected error occurs. */
    public List<Employee> getAllEmployeesFromDept(int deptId) {
        try {
            return _empRepo.findByDept_DeptId(deptId);
        } catch (DataAccessException e) {
            System.out.println("Database access error while fetching employees: " + e.getMessage());
            throw new RuntimeException("Database error: Unable to fetch employees.");
        } catch (Exception e) {
            System.out.println("Unexpected error while fetching employees: " + e.getMessage());
            throw new RuntimeException("An unexpected error occurred while fetching employees.");
        }
    }
}
