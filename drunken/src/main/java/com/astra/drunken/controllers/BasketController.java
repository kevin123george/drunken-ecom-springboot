package com.astra.drunken.controllers;

import com.astra.drunken.services.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Controller("/basket")
public class BasketController {

    private final BasketService basketService;

    @Autowired
    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }


//    @GetMapping("/add/bottle/{id}")
//    String addToBasket(Authentication authentication, Model model, @PathVariable Long id) {
//        basketService.addBottleToOrder(authentication, id);
//        return "product-page";
//    }
}
