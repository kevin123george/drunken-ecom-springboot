package com.astra.drunken.controllers;

import com.astra.drunken.services.BasketService;
import com.astra.drunken.services.BottleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/bottle")
public class ProductController {

    private final BottleService bottleService;
    private final BasketService basketService;

    @Autowired
    public ProductController(BottleService bottleService, BasketService basketService) {
        this.bottleService = bottleService;
        this.basketService = basketService;
    }

    @GetMapping("/{id}")
    String productDetails(Authentication authentication, Model model, @PathVariable Long id) {
//        basketService.addBottleToOrder(authentication, id);
        model.addAttribute("product", bottleService.getProductById(id).get());
        return "product-page";
    }

    @GetMapping("/add/{id}")
    String addToBasket(Authentication authentication, Model model, @PathVariable Long id) {
        model.addAttribute("product", bottleService.getProductById(id).get());
        basketService.addBottleToOrder(authentication, id);
        return "product-page";
    }


}
