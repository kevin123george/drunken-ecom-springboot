package com.astra.drunken.services;

import com.astra.drunken.controllers.DTOs.ProductResponseTO;
import com.astra.drunken.models.Beverage;
import com.astra.drunken.models.Bottle;
import com.astra.drunken.models.OrderItem;
import com.astra.drunken.repositories.BottleRepo;
import com.astra.drunken.repositories.UserRepo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BottleService {


    private final BottleRepo bottleRepo;
    private final UserRepo userRepo;
    private final OrderService orderService;

    public BottleService(BottleRepo bottleRepo, UserRepo userRepo, OrderService orderService) {
        this.bottleRepo = bottleRepo;
        this.userRepo = userRepo;
        this.orderService = orderService;
    }


    public List<Bottle> getAllBottles() {
        return bottleRepo.findAll();
    }

    public Optional<Bottle> getProductById(Long id) {
        return bottleRepo.findById(id);
    }

    public void addRandom() {
        var bottle = new Bottle();
        bottle.setName(String.valueOf(UUID.randomUUID()));
        bottle.setInStock(34);
        bottle.setIsAlcoholic(true);
        bottle.setPrice(343.0);
        bottle.setVolume(54.3);
        bottle.setSupplier("astra drug");
        bottleRepo.save(bottle);

    }

    public List<Bottle> productSearch(String q) {
        return bottleRepo.findByNameContaining(q);
    }

    public ProductResponseTO getBottleTo(Long id) {
        var bottle = bottleRepo.findById(id);
        return new ProductResponseTO(bottle.get());
    }

    @Transactional
    public String addBottleToOrder(Authentication authentication, Long bottleId) {

        var bottle = getProductById(bottleId).get();
        if (bottle.getInStock() > 0) {
            var user = userRepo.findByUserName(authentication.getName());
            var order = orderService.getOrderByUserAndActive(user.get());
            var orderItem = new OrderItem();
            var beverage = new Beverage();
            var orderItems = order.getOrderItems();
            beverage.setBottle(bottle);
            orderItem.setBeverage(beverage);
            orderItem.setOrder(order);
            orderItem.setPrice(bottle.getPrice());
            orderItems.add(orderItem);
            beverage.setOrderItem(orderItem);
            order.setOrderItems(orderItems);
            order.setPrice(order.getPrice() + orderItem.getPrice());
            orderService.savOrder(order);

            bottle.setInStock(bottle.getInStock() - 1);
            bottleRepo.save(bottle);
            return "item added to cart";

        }
        else{
            return "Item not in stock";
        }
    }
}
