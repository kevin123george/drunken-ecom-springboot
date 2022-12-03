package com.astra.drunken.controllers;

import com.astra.drunken.repositories.OrderItemRepo;
import com.astra.drunken.services.OrderService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static java.util.Collections.emptyList;

@Controller
@RequestMapping("/orders/")
public class OrderController {

    private final OrderService orderService;
    private final OrderItemRepo orderItemRepo;

    public OrderController(OrderService orderService, OrderItemRepo orderItemRepo) {
        this.orderService = orderService;
        this.orderItemRepo = orderItemRepo;
    }

    @GetMapping("/order-summary/")
    String OrderSummary(Authentication authentication, Model model) {
        var order = orderService.getOrderByUserAndActive(authentication);
        model.addAttribute("order", order.isPresent() ? order.get() : null);
        return "order_summary";
    }

    @GetMapping("/checkout/{id}")
    String OrderSummary(Authentication authentication, @PathVariable Long id) {
        orderService.checkoutOrder(id);
        return "redirect:/";
    }
}
