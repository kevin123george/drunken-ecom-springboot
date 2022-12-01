package com.astra.drunken.services;

import com.astra.drunken.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;

public interface UserService extends UserDetailsService {
    User save(User registrationDto);

    Collection<? extends GrantedAuthority> getAuthorities();
}
