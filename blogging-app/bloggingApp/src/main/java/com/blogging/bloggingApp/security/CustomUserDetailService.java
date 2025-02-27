package com.blogging.bloggingApp.security;

import com.blogging.bloggingApp.Exceptions.ResourceNotFoundException;
import com.blogging.bloggingApp.Repositories.UserRepo;
import com.blogging.bloggingApp.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch user from the database
        User user = userRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        // Convert user roles into authorities
        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())) // Assuming role.getName() returns "ROLE_ADMIN", "ROLE_USER", etc.
                .collect(Collectors.toSet());

        // Return UserDetails object
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),  // Username (email)
                user.getPassword(),  // Password (must be encoded)
                authorities  // List of roles/authorities
        );
    }
}
