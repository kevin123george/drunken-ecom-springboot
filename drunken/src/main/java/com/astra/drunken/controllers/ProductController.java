package com.astra.drunken.controllers;

import com.astra.drunken.services.BottleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bottle")
public class ProductController {

    private final BottleService bottleService;

    @Autowired
    public ProductController(BottleService bottleService) {
        this.bottleService = bottleService;
    }

    @GetMapping("/{id}")
    String productDetails(Model model, @PathVariable Long id) {
        model.addAttribute("product", bottleService.getProductById(id).get());
        return "product-page";
    }
}
