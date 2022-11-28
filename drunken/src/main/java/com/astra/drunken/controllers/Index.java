package com.astra.drunken.controllers;

import com.astra.drunken.services.BasketService;
import com.astra.drunken.services.BottleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class Index {

    private final BottleService bottleService;
    private final BasketService basketService;

    @Autowired
    public Index(BottleService bottleService, BasketService basketService) {
        this.bottleService = bottleService;
        this.basketService = basketService;
    }

    @GetMapping("/")
    String getIndex(Model model) {
        model.addAttribute("productList", bottleService.getAllBottles());
        return "base";
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


}
