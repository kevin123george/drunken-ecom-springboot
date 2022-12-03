package com.astra.drunken.controllers;

import com.astra.drunken.repositories.OrderItemRepo;
import com.astra.drunken.services.BasketService;
import com.astra.drunken.services.BottleService;
import com.astra.drunken.services.CrateService;
import com.astra.drunken.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("/index")
public class Index {

    private final BottleService bottleService;
    private final BasketService basketService;
    private final OrderService orderService;
    private final OrderItemRepo orderItemRepo;
    private final CrateService crateService;

    @Autowired
    public Index(BottleService bottleService, BasketService basketService, OrderService orderService, OrderItemRepo orderItemRepo, CrateService crateService) {
        this.bottleService = bottleService;
        this.basketService = basketService;
        this.orderService = orderService;
        this.orderItemRepo = orderItemRepo;
        this.crateService = crateService;
    }

    @GetMapping("/")
    String getIndex(Model model) {
        model.addAttribute("productList", basketService.getAllProducts());
        return "exp";
    }

    @GetMapping("/add-random")
    String addRandomProducts(Authentication authentication, Model model) {
        basketService.addToBasket(authentication, 2L);
        bottleService.addRandom();
        return "exp";
    }

    @GetMapping("/search")
    String productSearch(Model model, @RequestParam String q) {
        model.addAttribute("productList", bottleService.productSearch(q));
        return "exp";
    }


}
