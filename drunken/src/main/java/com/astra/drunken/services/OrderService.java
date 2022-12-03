package com.astra.drunken.services;

import com.astra.drunken.models.Order;
import com.astra.drunken.models.User;
import com.astra.drunken.repositories.OrderRepo;
import com.astra.drunken.repositories.UserRepo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

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

    public void checkoutOrder(Long orderId){
        var order = orderRepo.findById(orderId);
        order.get().setIsActive(false);
        orderRepo.save(order.get());
    }

    /**
     * I check if the customer already have a active Cart if so return else return new Cart.
     *
     * @param user - the current user
     * @return Cart
     */
    public Order getOrderByUserAndActive(User user) {
        var oldOrder = orderRepo.findByUserAndIsActive(user, true);
        if (oldOrder.isPresent()) {
            return oldOrder.get();
        } else {
            var newOrder = new Order();
            newOrder.setUser(user);
            return orderRepo.save(newOrder);
//            return newOrder;
        }
    }

}
