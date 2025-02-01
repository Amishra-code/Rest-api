package com.crud.Rest.api.controller;
import com.crud.Rest.api.model.Employee;
import com.crud.Rest.api.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    /** Creates a new employee and returns the saved employee with 201 status, or 500 if an error occurs. */
    @PostMapping("/employee/create")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee emp) {
        Employee savedEmployee = service.createEmployee(emp);
        System.out.println(emp);
        if (savedEmployee != null) {
            return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /** Retrieves a list of all employees and returns it with a 302 Found status. */
    @GetMapping("/employees")
    public ResponseEntity<Page<Employee>> getAllEmployees(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        return new ResponseEntity<>(service.getAllEmployees(pageNumber, pageSize), HttpStatus.FOUND);
    }

    /** Retrieves an employee by ID and returns it with 302 Found status if found, or 404 Not Found if not. */
    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable int id) {
        Employee fetchedEmployee = service.getEmployee(id);
        if (fetchedEmployee != null) {
            return new ResponseEntity<>(fetchedEmployee, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /** Updates an employee by ID and returns 200 OK if successful, or 400 Bad Request if update fails. */
    @PutMapping("/employee/{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable int id, @RequestBody Employee emp) throws IOException {
        Employee updatedEmployee = null;
        try {
            updatedEmployee=service.updateEmployee(id, emp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(updatedEmployee!=null)
            return new ResponseEntity<>("Updated", HttpStatus.OK);
        else
            return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);
    }

    /** Deletes an employee by ID and returns 200 OK if successful, or 404 Not Found if the employee doesn't exist. */
    @DeleteMapping("/employee/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
        Employee deletedEmployee = service.getEmployee(id);
        if(deletedEmployee!=null) {
            service.deleteEmployee(id);
            return new ResponseEntity<>("Deleted Employee's data", HttpStatus.OK);
        }
        return new ResponseEntity<>("Employee does not exist", HttpStatus.NOT_FOUND);
    }

}
