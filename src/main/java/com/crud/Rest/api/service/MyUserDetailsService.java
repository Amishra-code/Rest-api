package com.crud.Rest.api.service;

import com.crud.Rest.api.model.Users;
import com.crud.Rest.api.model.UserPrincipal;
import com.crud.Rest.api.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo _userRepo;

    /** Loads a user by username, throwing a UsernameNotFoundException if the user doesn't exist. */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = _userRepo.findByUsername(username);

        if (users == null) {
            System.out.println("User 404");
            throw new UsernameNotFoundException("User 404");
        }
        return new UserPrincipal(users);
    }
}
