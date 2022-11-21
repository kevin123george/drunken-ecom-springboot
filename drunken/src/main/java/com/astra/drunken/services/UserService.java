package com.astra.drunken.services;

import com.astra.drunken.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User save(User registrationDto);
}
