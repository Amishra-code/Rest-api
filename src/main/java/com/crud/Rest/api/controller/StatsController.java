package com.crud.Rest.api.controller;

import com.crud.Rest.api.model.Employee;
import com.crud.Rest.api.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StatsController {
    @Autowired
    private StatsService service;

    /** Retrieves a list of all employees with specific cityId and returns it with a 302 Found status. */
    @GetMapping("/employees_city/{cityId}")
    public ResponseEntity<List<Employee>> getAllEmployeesFromCity(@PathVariable int cityId) {
        return new ResponseEntity<>(service.getAllEmployeesFromCity(cityId), HttpStatus.FOUND);
    }

    /** Retrieves a list of all employees with specific deptId and returns it with a 302 Found status. */
    @GetMapping("/employees_dept/{deptId}")
    public ResponseEntity<List<Employee>> getAllEmployeesFromDept(@PathVariable int deptId) {
        return new ResponseEntity<>(service.getAllEmployeesFromDept(deptId), HttpStatus.FOUND);
    }
}
