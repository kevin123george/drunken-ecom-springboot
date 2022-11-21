package com.astra.drunken.repositories;

import com.astra.drunken.models.Bottle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BottleRepo  extends JpaRepository<Bottle, Long> {
}
