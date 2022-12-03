package com.astra.drunken.services;

import com.astra.drunken.models.User;
import com.astra.drunken.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepo userRepository) {
        super();
        this.userRepo = userRepo;
    }

    @Override
    public User save(User registrationDto) {
        registrationDto.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        return userRepo.save(registrationDto);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var user = userRepo.findByUserName(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.get().getUserName(), user.get().getPassword(), getAuthorities());
//        return (UserDetails) user.get();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

}
