package com.astra.drunken.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class Index {

    @GetMapping("/")
    String getIndex(Model model){
//        model.addAttribute("productList");
        return "index";
    }

}
