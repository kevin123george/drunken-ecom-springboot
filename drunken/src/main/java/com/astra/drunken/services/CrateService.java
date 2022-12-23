package com.astra.drunken.services;

import com.astra.drunken.controllers.DTOs.ProductResponseTO;
import com.astra.drunken.models.Beverage;
import com.astra.drunken.models.Crate;
import com.astra.drunken.models.OrderItem;
import com.astra.drunken.repositories.CrateRepo;
import com.astra.drunken.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CrateService {
    private final CrateRepo crateRepo;
    private final OrderService orderService;
    private final UserRepo userRepo;

    @Autowired
    public CrateService(CrateRepo crateRepo, OrderService orderService, UserRepo userRepo) {
        this.crateRepo = crateRepo;
        this.orderService = orderService;
        this.userRepo = userRepo;
    }

    public List<Crate> getAllCrates() {
        return crateRepo.findAll();
    }

    public ProductResponseTO getProductById(Long id) {
        return new ProductResponseTO(crateRepo.findById(id).get());
    }

    public Optional<Crate> getCrateById(Long id) {
        return crateRepo.findById(id);
    }

    public void addRandom() {
        var bottle = new Crate();
        bottle.setName(String.valueOf(UUID.randomUUID()));
        crateRepo.save(bottle);

    }

    public ProductResponseTO getCrateTo(Long id) {
        var crate = crateRepo.findById(id);
        return new ProductResponseTO(crate.get());
    }

    /**
     * I check if the customer already have a active Cart if so return else return new Cart.
     *
     * @param authentication - the current authenticated user
     * @param crateId       - opted bottle
     */
    @Transactional
    public void addCrateToOrder(Authentication authentication, Long crateId) {
        // TODO: 28/11/2022  move this to a consturtor
        // TODO: 01/12/2022  checkc add to basket only adding one item ata a rime

        var crate = getCrateById(crateId).get();
        if(crate.getCrateInStock() > 0){
            var user = userRepo.findByUserName(authentication.getName());
            var order = orderService.getOrderByUserAndActive(user.get());
            var orderItem = new OrderItem();
            var beverage = new Beverage();
            var orderItems = order.getOrderItems();
            beverage.setCrate(crate);
            orderItem.setOrder(order);
            orderItem.setPrice(crate.getPrice());
            orderItems.add(orderItem);
            orderItem.setPrice(crate.getPrice());
            order.setOrderItems(orderItems);
            order.setPrice(order.getPrice() + orderItem.getPrice());
            crate.setCrateInStock(crate.getCrateInStock()-1);
            orderItem.setBeverage(beverage);
            beverage.setOrderItem(orderItem);
            orderService.savOrder(order);
            crateRepo.save(crate);

        }

    }

    public Crate saveCrate(Crate crate) {
        return crateRepo.save(crate);
    }
}
