package com.astra.drunken.services;

import com.astra.drunken.controllers.DTOs.ProductResponseTO;
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

    public Optional<Crate> getProductById(Long id) {
        return crateRepo.findById(id);
    }

    public void addRandom() {
        var bottle = new Crate();
        bottle.setName(String.valueOf(UUID.randomUUID()));
        crateRepo.save(bottle);

    }

    public ProductResponseTO getBottleTo(Long id) {
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

        var crate = getProductById(crateId).get();
        if(crate.getCrateInStock() > 0){
            var user = userRepo.findByUserName(authentication.getName());
            var order = orderService.getOrderByUserAndActive(user.get());
            var orderItem = new OrderItem();
            var orderItems = order.getOrderItems();
            orderItem.setOrder(order);
            orderItem.setPrice(crate.getPrice());
            orderItems.add(orderItem);
            order.setOrderItems(orderItems);
            crate.setCrateInStock(crate.getCrateInStock()-1);
            orderService.savOrder(order);
        }

    }

    public Crate saveCrate(Crate crate) {
        return crateRepo.save(crate);
    }
}
