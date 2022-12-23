package com.astra.drunken.services;

import com.astra.drunken.controllers.DTOs.ProductResponseTO;
import com.astra.drunken.models.Bottle;
import com.astra.drunken.models.Order;
import com.astra.drunken.models.OrderItem;
import com.astra.drunken.repositories.BottleRepo;
import com.astra.drunken.repositories.CrateRepo;
import com.astra.drunken.repositories.OrderItemRepo;
import com.astra.drunken.repositories.OrderRepo;
import com.astra.drunken.utils.BEtoToConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BasketService {

    private final BottleService bottleService;
    private final OrderRepo orderRepo;
    private final OrderItemRepo orderItemRepo;
    private final BottleRepo bottleRepo;
    private final CrateRepo crateRepo;

    private final CrateService crateService;
    private final OrderService orderService;


    @Autowired
    public BasketService(BottleService bottleService, OrderRepo orderRepo, OrderItemRepo orderItemRepo, BottleRepo bottleRepo, CrateRepo crateRepo, CrateService crateService, OrderService orderService) {
        this.bottleService = bottleService;
        this.orderRepo = orderRepo;
        this.orderItemRepo = orderItemRepo;
        this.bottleRepo = bottleRepo;
        this.crateRepo = crateRepo;
        this.crateService = crateService;
        this.orderService = orderService;
    }

    public Order initializeOrder() {
        var order = new Order();
        order = orderRepo.save(order);
        return order;
    }

//    public void addToBasket(Authentication authentication, Long id) {
//        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        var user = userRepo.findByUserName(authentication.getName());
//        var item = bottleService.getProductById(id);
//        if (item.isPresent()) {
//            var order = orderRepo.findByUserAndIsActive(user.get(), true);
//            if (order.isPresent()) {
//
//            } else {
//                var newOrder = new Order();
//                newOrder.setUser(user.get());
//                var orderItem = new OrderItem();
//                orderItem.setPrice(item.get().getPrice());
//                orderItem.setPosition(item.get().getName());
//                Set<OrderItem> orderSet = new java.util.HashSet<>(Collections.emptySet());
//                orderSet.add(orderItem);
//                newOrder.setOrderItems(orderSet);
//                orderItem.setOrder(newOrder);
//                orderRepo.save(newOrder);
//            }
//        }
//    }


    /**
     * check out order with order id
     *
     * @param orderId       - orderId
     */
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
        var bottleResponseTO = BEtoToConverter.convertBottleBEToTO(bottle);
        return new ArrayList<>(bottleResponseTO);
    }

    public List<ProductResponseTO> getAllCrates() {
        var crates = crateService.getAllCrates();
        var crateResponseTO = BEtoToConverter.convertCrateBEToTO(crates);
        return new ArrayList<>(crateResponseTO);
    }


    public void removeItem(Authentication authentication, Long itemId) {
        var item = orderItemRepo.findById(itemId).get();
        var itemPrice = item.getPrice();
        var order = orderService.getOrderByUserAndActive(authentication).get();
        var currentTotal = order.getPrice() - itemPrice;
        order.setPrice(currentTotal);
        updateItemsCount(item);
        orderRepo.save(order);
        orderItemRepo.deleteById(itemId);
        }


    @Transactional
    public void removeAllItems(Authentication authentication) {
        var order = orderService.getOrderByUserAndActive(authentication).get();
        var items = order.getOrderItems();
        order.setPrice(0.0);
        orderItemRepo.deleteByOrder(order);
        for(OrderItem item : items){
            updateItemsCount(item);
        }
    }

    public void updateItemsCount(OrderItem item){

        if (item.getBeverage().getBottle() != null){
            var bottle = item.getBeverage().getBottle();
            bottle.setInStock(bottle.getInStock() + 1);
            bottleService.saveBottle(bottle);
        }
        else{
            var crate = item.getBeverage().getCrate();
            crate.setCrateInStock(crate.getCrateInStock() +1);
            crateService.saveCrate(crate);
        }
    }

    public List<ProductResponseTO> productSearch(String q) {
        List<ProductResponseTO> productsResponseTO = new ArrayList<>();
        var bottleResponseTO = BEtoToConverter.convertBottleBEToTO(bottleRepo.findByNameContaining(q));
        var crateResponseTO = BEtoToConverter.convertCrateBEToTO(crateRepo.findByNameContaining(q));
        productsResponseTO.addAll(bottleResponseTO);
        productsResponseTO.addAll(crateResponseTO);
        return productsResponseTO;
    }
}
