package com.astra.drunken.controllers;

import com.astra.drunken.services.BasketService;
import com.astra.drunken.services.BottleService;
import com.astra.drunken.services.GoogleCloudFileUpload;
import com.astra.drunken.services.TemplateHelper;
import com.google.api.client.util.Value;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
@RequestMapping("/bottle")
public class BottleController {

    private final BottleService bottleService;
    private final BasketService basketService;
    private final TemplateHelper templateHelper;
    private final GoogleCloudFileUpload googleCloudFileUpload;
    @Value("${file.storage}")
    private Resource localFilePath;
    private Storage storage;
    public BottleController(BottleService bottleService, BasketService basketService, TemplateHelper templateHelper, GoogleCloudFileUpload googleCloudFileUpload) {
        this.bottleService = bottleService;
        this.basketService = basketService;
        this.templateHelper = templateHelper;
        this.googleCloudFileUpload = googleCloudFileUpload;
    }

    @GetMapping("/{id}")
    String productDetails(Authentication authentication, Model model, @PathVariable Long id) {
        model.addAttribute("itemType", 1);
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
