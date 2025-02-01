package com.crud.Rest.api.service;
import com.crud.Rest.api.model.Users;
import com.crud.Rest.api.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private JWTService _jwtService;

    @Autowired
    AuthenticationManager _authManager;

    @Autowired
    private UserRepo _userRepo;

    /** Registers a new user by encoding the password and saving the user to the repository. */
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    public Users register(Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        _userRepo.save(user);
        return user;
    }

    /** Verifies the user's credentials and returns a JWT token if authentication is successful. */
    public String verify(Users user) {
        Authentication authentication = _authManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );
        if (authentication.isAuthenticated()) {
            Users userDetails = _userRepo.findByUsername(user.getUsername());
            if (userDetails != null) {
                return _jwtService.generateToken(user.getUsername(), userDetails.getRole());
            } else {
                return "user not found";
            }
        } else {
            return "failed";
        }
    }
}
