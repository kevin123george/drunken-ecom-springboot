package com.astra.drunken.services;

import com.astra.drunken.models.Crate;
import com.astra.drunken.models.Order;
import com.astra.drunken.repositories.OrderRepo;
import com.astra.drunken.repositories.UserRepo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepo orderRepo;
    private final UserRepo userRepo;

    public OrderService(OrderRepo orderRepo, UserRepo userRepo) {
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
    }

    public Optional<Order> getOrderByUserAndActive(Authentication authentication) {
        var user = userRepo.findByUserName(authentication.getName());

        return orderRepo.findByUserAndIsActive(user.get(), true);
    }

}
