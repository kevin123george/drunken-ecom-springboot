package com.astra.drunken.controllers;

import com.astra.drunken.services.BasketService;
import com.astra.drunken.services.CrateService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/crate")
public class CarteController {

    private final CrateService crateService;
    private final BasketService basketService;

    public CarteController(CrateService crateService, BasketService basketService) {
        this.crateService = crateService;
        this.basketService = basketService;
    }

    @GetMapping("/{id}")
    String productDetails(Authentication authentication, Model model, @PathVariable Long id) {
        model.addAttribute("product", crateService.getBottleTo(id));
        return "product-page";
    }

    @GetMapping("/add/{id}")
    String addToBasket(Authentication authentication, Model model, @PathVariable Long id) {
        model.addAttribute("product", crateService.getProductById(id).get());
        crateService.addCrateToOrder(authentication, id);
        return "product-page";
    }
    @GetMapping("")
    String getIndex(Model model) {
        model.addAttribute("productList", basketService.getAllCrates());
        return "exp";
    }

}
