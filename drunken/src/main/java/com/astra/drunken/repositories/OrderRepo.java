package com.astra.drunken.repositories;

import com.astra.drunken.models.Order;
import com.astra.drunken.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepo extends JpaRepository<Order, Long> {
    Optional<List<Order>> findByUserAndIsActive(User user, Boolean isActive);
    Optional<List<Order>> findByIsActive(Boolean isActive);
}
