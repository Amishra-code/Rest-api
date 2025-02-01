package com.crud.Rest.api.repository;

import com.crud.Rest.api.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer> {

    Optional<Employee> findById(int id);

    @Query("SELECT e FROM Employee e JOIN FETCH e.city JOIN FETCH e.dept")
    List<Employee> findAllEmployeesWithCityAndDepartment();

    List<Employee> findByCity_CityId(int cityId);
    List<Employee> findByDept_DeptId(int deptId);
}
