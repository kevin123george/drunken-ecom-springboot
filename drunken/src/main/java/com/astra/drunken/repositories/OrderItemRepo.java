package com.astra.drunken.repositories;

import com.astra.drunken.models.Order;
import com.astra.drunken.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrder(Order order);

    void deleteByOrder(Order order);
}
