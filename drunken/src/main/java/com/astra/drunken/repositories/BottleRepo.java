package com.astra.drunken.repositories;

import com.astra.drunken.models.Bottle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BottleRepo extends JpaRepository<Bottle, Long> {
    List<Bottle> findByNameContaining(String q);
}
