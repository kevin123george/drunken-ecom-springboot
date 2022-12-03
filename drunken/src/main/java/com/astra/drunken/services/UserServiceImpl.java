package com.astra.drunken.services;

import com.astra.drunken.controllers.DTOs.UserResposeTo;
import com.astra.drunken.models.User;
import com.astra.drunken.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public User getUserByName(String userName) {
        return userRepo.findByUserName(userName).get();
    }

    public UserResposeTo getUserTo(Authentication authentication) {
        var user = userRepo.findByUserName(authentication.getName());
        return new UserResposeTo(user.get());
    }

    @Transactional
    public User editUser(User newUser, Authentication authentication) {
        var user = userRepo.findByUserName(authentication.getName());
        user.get().setPassword(passwordEncoder.encode(newUser.getPassword()));
        user.get().setBirthDate(newUser.getBirthDate());
        return userRepo.save(user.get());

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
