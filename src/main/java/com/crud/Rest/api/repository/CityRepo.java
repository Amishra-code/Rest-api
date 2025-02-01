package com.crud.Rest.api.repository;

import com.crud.Rest.api.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepo extends JpaRepository<City, Integer> {
    Optional<City> findById(int id);
}
