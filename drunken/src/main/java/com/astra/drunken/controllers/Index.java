package com.astra.drunken.controllers;

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
    private final TemplateHelper templateHelper;
    private final GoogleCloudFileUpload googleCloudFileUpload;

    @Autowired
    public Index(BottleService bottleService, BasketService basketService, TemplateHelper templateHelper, GoogleCloudFileUpload googleCloudFileUpload) {
        this.bottleService = bottleService;
        this.basketService = basketService;
        this.templateHelper = templateHelper;
        this.googleCloudFileUpload = googleCloudFileUpload;
    }

    @GetMapping("/")
    String getIndex(Authentication authentication, Model model) {
        model.addAttribute("productList", basketService.getAllProducts());
            templateHelper.defaultTemplateModel(model, authentication);

//            googleCloudFileUpload.upload("sdfsdf");

        return "exp";
    }

    @GetMapping("/add-random")
    String addRandomProducts(Authentication authentication, Model model) {
//        basketService.addToBasket(authentication, 2L);
        return "exp";
    }

    @GetMapping("/search")
    String productSearch(Model model, @RequestParam String q, Authentication authentication) {
        model.addAttribute("productList", basketService.productSearch(q));
        templateHelper.defaultTemplateModel(model, authentication);
        return "exp";
    }
}
