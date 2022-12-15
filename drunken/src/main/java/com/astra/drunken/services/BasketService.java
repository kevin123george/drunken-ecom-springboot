package com.astra.drunken.services;

import com.astra.drunken.controllers.DTOs.ProductResponseTO;
import com.astra.drunken.models.Order;
import com.astra.drunken.models.OrderItem;
import com.astra.drunken.repositories.OrderItemRepo;
import com.astra.drunken.repositories.OrderRepo;
import com.astra.drunken.repositories.UserRepo;
import com.astra.drunken.utils.BEtoToConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class BasketService {

    private final BottleService bottleService;
    private final OrderRepo orderRepo;
    private final OrderItemRepo orderItemRepo;
    private final UserService userService;
    private final UserRepo userRepo;
    private final CrateService crateService;
    private final OrderService orderService;

    @Autowired
    public BasketService(BottleService bottleService, OrderRepo orderRepo, OrderItemRepo orderItemRepo, UserService userService, UserRepo userRepo, CrateService crateService, OrderService orderService) {
        this.bottleService = bottleService;
        this.orderRepo = orderRepo;
        this.orderItemRepo = orderItemRepo;
        this.userService = userService;
        this.userRepo = userRepo;
        this.crateService = crateService;
        this.orderService = orderService;
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
     * @param authentication - the current authenticated user
     * @param bottleId       - opted bottle
     */
    public void addBottleToOrder(Authentication authentication, Long bottleId) {
        // TODO: 28/11/2022  move this to a consturtor
        // TODO: 01/12/2022  checkc add to basket only adding one item ata a rime

        var bottle = bottleService.getProductById(bottleId).get();
        var user = userRepo.findByUserName(authentication.getName());
        var order = orderService.getOrderByUserAndActive(user.get());
        var orderItem = new OrderItem();
        var orderItems = order.getOrderItems();
        orderItem.setOrder(order);
        orderItem.setPrice(bottle.getPrice());
        orderItems.add(orderItem);
        orderItemRepo.saveAll(orderItems);

    }

    public void checkout(Long orderId) {
        var order = orderRepo.findById(orderId);
        order.get().setIsActive(false);
        orderRepo.save(order.get());
    }

    public List<ProductResponseTO> getAllProducts() {
        var bottle = bottleService.getAllBottles();
        var crates = crateService.getAllCrates();
        List<ProductResponseTO> productsResponseTO = new ArrayList<>();
        var bottleResponseTO = BEtoToConverter.convertBottleBEToTO(bottle);
        var crateResponseTO = BEtoToConverter.convertCrateBEToTO(crates);
        productsResponseTO.addAll(bottleResponseTO);
        productsResponseTO.addAll(crateResponseTO);
        return productsResponseTO;
    }

    public List<ProductResponseTO> getAllBottles() {
        var bottle = bottleService.getAllBottles();
        List<ProductResponseTO> productsResponseTO = new ArrayList<>();
        var bottleResponseTO = BEtoToConverter.convertBottleBEToTO(bottle);
        productsResponseTO.addAll(bottleResponseTO);
        return productsResponseTO;
    }

    public List<ProductResponseTO> getAllCrates() {
        var crates = crateService.getAllCrates();
        List<ProductResponseTO> productsResponseTO = new ArrayList<>();
        var crateResponseTO = BEtoToConverter.convertCrateBEToTO(crates);
        productsResponseTO.addAll(crateResponseTO);
        return productsResponseTO;
    }


    public void removeItem(Long itemId){
        orderItemRepo.deleteById(itemId);
    }


}
