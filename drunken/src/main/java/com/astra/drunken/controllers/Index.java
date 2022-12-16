package com.astra.drunken.controllers;

import com.astra.drunken.repositories.OrderItemRepo;
import com.astra.drunken.services.*;
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
    private final TemplateHelper templateHelper;

    @Autowired
    public Index(BottleService bottleService, BasketService basketService, OrderService orderService, OrderItemRepo orderItemRepo, CrateService crateService, TemplateHelper templateHelper) {
        this.bottleService = bottleService;
        this.basketService = basketService;
        this.orderService = orderService;
        this.orderItemRepo = orderItemRepo;
        this.crateService = crateService;
        this.templateHelper = templateHelper;
    }

    @GetMapping("/")
    String getIndex(Authentication authentication, Model model) {
        bottleService.addRandom();
        model.addAttribute("productList", basketService.getAllProducts());
        templateHelper.defaultTemplateModel(model, authentication);
        return "exp";
    }

    @GetMapping("/add-random")
    String addRandomProducts(Authentication authentication, Model model) {
//        basketService.addToBasket(authentication, 2L);
        bottleService.addRandom();
        return "exp";
    }

    @GetMapping("/search")
    String productSearch(Model model, @RequestParam String q, Authentication authentication) {

        model.addAttribute("productList", bottleService.productSearch(q));
        templateHelper.defaultTemplateModel(model, authentication);
        return "exp";
    }


}
