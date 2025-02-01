package com.crud.Rest.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private UserDetailsService userDetailsService;

    /** Configures security settings, including CSRF, request authorization, and stateless session management. */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(customizer -> customizer.disable())
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/login", "/register").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/employee/**").hasAuthority("admin")
                        .requestMatchers(HttpMethod.GET, "/api/employees").hasAnyAuthority("admin", "user")
                        .requestMatchers(HttpMethod.GET, "/api/employee/**").hasAnyAuthority("admin", "user")
                        .requestMatchers(HttpMethod.PUT, "/api/employee/**").hasAnyAuthority("admin", "user")
                        .requestMatchers(HttpMethod.POST, "/api/employee/create").hasAnyAuthority("admin", "user")
                        .requestMatchers(HttpMethod.GET, "/api/employees_city").hasAnyAuthority("admin", "user")
                        .requestMatchers(HttpMethod.GET, "/api/employees_dept").hasAnyAuthority("admin", "user")
                        .requestMatchers("/api", "/employee", "/employee/**").authenticated()
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /** Provides a DaoAuthenticationProvider with a BCrypt password encoder and custom user details service. */
    @Bean
    public DaoAuthenticationProvider authenicationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    /** Configures and returns an AuthenticationManager from the provided AuthenticationConfiguration. */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}