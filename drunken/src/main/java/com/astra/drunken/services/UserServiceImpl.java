package com.astra.drunken.services;

import com.astra.drunken.controllers.DTOs.AddressResposeTo;
import com.astra.drunken.controllers.DTOs.UserResposeTo;
import com.astra.drunken.models.*;
import com.astra.drunken.repositories.AddressRepo;
import com.astra.drunken.repositories.RolesRepo;
import com.astra.drunken.repositories.UserRepo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final RolesRepo rolesRepo;

    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepo userRepo, AddressRepo addressRepo, RolesRepo rolesRepo, BCryptPasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.rolesRepo = rolesRepo;
        this.passwordEncoder = passwordEncoder;
    }


    public User getUserByName(String userName) {
        return userRepo.findByUserName(userName).get();
    }

    public UserResposeTo getUserTo(Authentication authentication) {
        var user = userRepo.findByUserName(authentication.getName());
        return new UserResposeTo(user.get());
    }


    public AddressResposeTo getUserAddressTo(Authentication authentication, AddressType addressType) {
        var user = userRepo.findByUserName(authentication.getName());
        return new AddressResposeTo(user.get(), addressType);
    }

    @Transactional
    public User editUser(User newUser, Authentication authentication) {
        var user = userRepo.findByUserName(authentication.getName());
        user.get().setPassword(passwordEncoder.encode(newUser.getPassword()));
        user.get().setBirthDate(newUser.getBirthDate());
        return userRepo.save(user.get());

    }

    @Transactional
    public User editAddress(Address address, Authentication authentication) {
        var user = userRepo.findByUserName(authentication.getName());
        var currentAddress = user.get().getAddress();
        var d = currentAddress.stream().filter(a ->
            address.getAddressType() == a.getAddressType()
        );
        currentAddress.remove(d);
        currentAddress.add(address);

//        address.setUser(user.get());
        user.get().setAddress(currentAddress);
        return userRepo.save(user.get());
//        return userRepo.save(user.get());

    }

    @Override
    public User save(User registrationDto) {
        registrationDto.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        if (registrationDto.getRoles().isEmpty()){
            var roles = new HashSet<Role>();
            roles.add(rolesRepo.findByName(ERole.ROLE_MODERATOR).get());
            registrationDto.setRoles(roles);

        }
        return userRepo.save(registrationDto);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findByUserName(username).get();
        if(user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

}
