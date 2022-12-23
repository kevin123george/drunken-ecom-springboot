package com.astra.drunken.services;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class TemplateHelper {

    private final OrderService orderService;

    public TemplateHelper(OrderService orderService) {
        this.orderService = orderService;
    }

    public void defaultTemplateModel(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated() & orderService.getOrderByUserAndActive(authentication).isPresent()) {
            model.addAttribute("haveActiveCart", true);
            model.addAttribute("itemCount", orderService.getOrderByUserAndActive(authentication).get().getOrderItems().size());
        }
    }
}
