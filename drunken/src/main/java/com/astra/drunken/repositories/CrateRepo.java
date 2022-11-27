package com.astra.drunken.repositories;

import com.astra.drunken.models.Crate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrateRepo extends JpaRepository<Crate, Long> {
}
