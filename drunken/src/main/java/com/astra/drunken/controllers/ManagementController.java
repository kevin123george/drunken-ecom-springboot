package com.astra.drunken.controllers;


import com.astra.drunken.models.Bottle;
import com.astra.drunken.models.Crate;
import com.astra.drunken.services.BottleService;
import com.astra.drunken.services.CrateService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/shopkeeper")
public class ManagementController {

    private final CrateService crateService;
    private final BottleService bottleService;


    public ManagementController(CrateService crateService, BottleService bottleService) {
        this.crateService = crateService;
        this.bottleService = bottleService;
    }


//    @PostMapping( value = "/carte")
//    public ResponseEntity<Crate> addCrate(@RequestBody Crate crate) {
//        System.out.println("sadsadasdasdasdasdsad");
//        return ResponseEntity.ok(crateService.saveCrate(crate));
//    }
//
//    @PostMapping( value = "/bottle")
//    public ResponseEntity<Bottle> addBottle(@RequestBody Bottle bottle) {
//        return ResponseEntity.ok(bottleService.saveBottle(bottle));
//    }


    @PreAuthorize("hasRole('MODERATOR')")
    @GetMapping("/bottle")
    public String addBottle(Authentication authentication, Model model) {
        model.addAttribute("bottle",new Bottle());
        return "add-product";
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @PostMapping("/bottle")
    public String addBottle(Authentication authentication, @Valid @ModelAttribute("bottle") Bottle bottle, BindingResult error, Model model) {
        if (error.hasErrors()){
//            model.addAttribute("crate", );
            return "add-product";
        }
        bottleService.saveBottle(bottle);
        return "redirect:/";
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @GetMapping("/crate")
    public String addCrate(Authentication authentication, Model model) {
        model.addAttribute("crate",new Crate());
        return "add-crate";
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @PostMapping("/crate")
    public String addCrate(Authentication authentication, @Valid @ModelAttribute("crate") Crate crate, BindingResult error, Model model) {
        if (error.hasErrors()){
//            model.addAttribute("crate", );
            return "add-crate";
        }
        crateService.saveCrate(crate);
        return "redirect:/";
    }


}
