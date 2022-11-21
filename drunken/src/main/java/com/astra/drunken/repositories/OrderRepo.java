package com.astra.drunken.repositories;

import com.astra.drunken.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Long> {
}
