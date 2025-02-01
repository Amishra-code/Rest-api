package com.crud.Rest.api.repository;

import com.crud.Rest.api.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<Users, Integer> {
    Users findByUsername(String username);
}
