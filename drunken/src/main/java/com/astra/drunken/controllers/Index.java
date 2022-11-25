package com.astra.drunken.controllers;

import com.astra.drunken.services.BottleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class Index {

    @Autowired
    private BottleService bottleService;

    @GetMapping("/")
    String getIndex(Model model){
        model.addAttribute("productList",bottleService.getAllBottles());
        return "base";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/{id}")
    String productDetails(Model model,@PathVariable Long id){
        model.addAttribute("product", bottleService.getProductById(id).get());
        return "details-product";
    }

    @GetMapping("/add-random")
    String addRandomProducts(Model model){
        bottleService.addRandom();
        return "exp";
    }

    @GetMapping("/index")
    String index(Model model){
        model.addAttribute("productList",bottleService.getAllBottles());
        return "exp";
    }


}
