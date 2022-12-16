package com.astra.drunken.controllers;

import com.astra.drunken.services.BasketService;
import com.astra.drunken.services.BottleService;
import com.astra.drunken.services.TemplateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bottle")
public class BottleController {

    private final BottleService bottleService;
    private final BasketService basketService;

    private final TemplateHelper templateHelper;

    @Autowired
    public BottleController(BottleService bottleService, BasketService basketService, TemplateHelper templateHelper) {
        this.bottleService = bottleService;
        this.basketService = basketService;
        this.templateHelper = templateHelper;
    }

    @GetMapping("/{id}")
    String productDetails(Authentication authentication, Model model, @PathVariable Long id) {
        model.addAttribute("product", bottleService.getBottleTo(id));
        templateHelper.defaultTemplateModel(model, authentication);
        return "product-page";
    }

    @GetMapping("/add/{id}")
    String addToBasket(Authentication authentication, Model model, @PathVariable Long id) {
        model.addAttribute("product", bottleService.getBottleTo(id));
        var msg = bottleService.addBottleToOrder(authentication, id);
        model.addAttribute("message", msg);
        templateHelper.defaultTemplateModel(model, authentication);
        return "product-page";
    }

    @GetMapping("")
    String getIndex(Authentication authentication, Model model) {
        model.addAttribute("productList", basketService.getAllBottles());
        templateHelper.defaultTemplateModel(model, authentication);
        return "exp";
    }


}
