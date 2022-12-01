package com.astra.drunken.controllers;

import com.astra.drunken.services.BasketService;
import com.astra.drunken.services.BottleService;
import com.astra.drunken.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller("/index")
public class Index {

    private final BottleService bottleService;
    private final BasketService basketService;
    private final OrderService orderService;

    @Autowired
    public Index(BottleService bottleService, BasketService basketService, OrderService orderService) {
        this.bottleService = bottleService;
        this.basketService = basketService;
        this.orderService = orderService;
    }

    @GetMapping("/")
    String getIndex(Model model) {
        model.addAttribute("productList", bottleService.getAllBottles());
        return "exp";
    }


    @GetMapping("/add-random")
    String addRandomProducts(Authentication authentication, Model model) {
        basketService.addToBasket(authentication, 2L);
        bottleService.addRandom();
        return "exp";
    }

    @GetMapping("/index")
    String index(Model model) {
        model.addAttribute("productList", bottleService.getAllBottles());
        return "exp";
    }

    @GetMapping("/search")
    String productSearch(Authentication authentication, Model model, @RequestParam String q) {
        model.addAttribute("productList", bottleService.productSearch(q));
        return "exp";
    }


    @GetMapping("/order-summary/")
    String OrderSummary(Authentication authentication, Model model) {
        var order = orderService.getOrderByUserAndActive(authentication);
        var orderItems = order.get().getOrderItems();
        model.addAttribute("orderItems", orderItems);
        return "order_summary";
    }


}
