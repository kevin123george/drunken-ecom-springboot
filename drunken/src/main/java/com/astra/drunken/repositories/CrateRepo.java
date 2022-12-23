package com.astra.drunken.repositories;

import com.astra.drunken.models.Bottle;
import com.astra.drunken.models.Crate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CrateRepo extends JpaRepository<Crate, Long> {
    List<Crate> findByNameContaining(String q);
}
