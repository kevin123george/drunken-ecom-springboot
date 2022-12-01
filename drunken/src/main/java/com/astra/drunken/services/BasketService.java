package com.astra.drunken.services;

import com.astra.drunken.models.Order;
import com.astra.drunken.models.OrderItem;
import com.astra.drunken.models.User;
import com.astra.drunken.repositories.OrderItemRepo;
import com.astra.drunken.repositories.OrderRepo;
import com.astra.drunken.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Service
public class BasketService {

    private final BottleService bottleService;
    private final OrderRepo orderRepo;
    private final OrderItemRepo orderItemRepo;
    private final UserService userService;
    private final UserRepo userRepo;

    @Autowired
    public BasketService(BottleService bottleService, OrderRepo orderRepo, OrderItemRepo orderItemRepo, UserService userService, UserRepo userRepo) {
        this.bottleService = bottleService;
        this.orderRepo = orderRepo;
        this.orderItemRepo = orderItemRepo;
        this.userService = userService;
        this.userRepo = userRepo;
    }

    public Order initializeOrder() {
        var order = new Order();
        order = orderRepo.save(order);
        return order;
    }

    public void addToBasket(Authentication authentication, Long id) {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var user = userRepo.findByUserName(authentication.getName());
        var item = bottleService.getProductById(id);
        if (item.isPresent()) {
            var order = orderRepo.findByUserAndIsActive(user.get(), true);
            if (order.isPresent()) {

            } else {
                var newOrder = new Order();
                newOrder.setUser(user.get());
                var orderItem = new OrderItem();
                orderItem.setPrice(item.get().getPrice());
                orderItem.setPosition(item.get().getName());
                Set<OrderItem> orderSet = new java.util.HashSet<>(Collections.emptySet());
                orderSet.add(orderItem);
                newOrder.setOrderItems(orderSet);
                orderItem.setOrder(newOrder);
                orderRepo.save(newOrder);
            }
        }
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

    /**
     * I check if the customer already have a active Cart if so return else return new Cart.
     *
     * @param authentication - the current authenticated user
     * @param bottleId       - opted bottle
     */
    public void addBottleToOrder(Authentication authentication, Long bottleId) {
        // TODO: 28/11/2022  move this to a consturtor

        var bottle = bottleService.getProductById(bottleId).get();
        var user = userRepo.findByUserName(authentication.getName());
        var order = getOrderByUserAndActive(user.get());
        var orderItem = new OrderItem();
        var orderItems = order.getOrderItems();
        orderItem.setOrder(order);
        orderItem.setPrice(bottle.getPrice());
        orderItems.add(orderItem);
        orderItemRepo.saveAll(orderItems);

    }

}
