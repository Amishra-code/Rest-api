package com.crud.Rest.api.repository;

import com.crud.Rest.api.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepo extends  JpaRepository<Department, Integer> {
    Optional<Department> findById(int id);
}
