package com.astra.drunken.repositories;

import com.astra.drunken.models.ERole;
import com.astra.drunken.models.Role;
import com.astra.drunken.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesRepo extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}
