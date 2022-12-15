package com.astra.drunken.controllers;

import com.astra.drunken.models.OrderItem;
import com.astra.drunken.repositories.OrderItemRepo;
import com.astra.drunken.services.BasketService;
import com.astra.drunken.services.OrderService;
import com.astra.drunken.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;

@Controller
@RequestMapping("/orders/")
public class OrderController {

    private final OrderService orderService;
    private final OrderItemRepo orderItemRepo;
    private final BasketService basketService;
    private final UserService userService;

    public OrderController(OrderService orderService, OrderItemRepo orderItemRepo, BasketService basketService, UserService userService) {
        this.orderService = orderService;
        this.orderItemRepo = orderItemRepo;
        this.basketService = basketService;
        this.userService = userService;
    }

    @GetMapping("/order-summary/")
    String rderSummary(Authentication authentication, Model model) {
        var order = orderService.getOrderByUserAndActive(authentication);
        var orderItems =  Collections.<OrderItem>emptyList();

        if (order.isPresent()){
            orderItems = orderItemRepo.findByOrder(order.get());
        }
//        model.addAttribute("order", orderItems.orElse(null));
        model.addAttribute("order", orderItems);
        model.addAttribute("orderId", order.isPresent() ? order.get().getId() : "0");
        model.addAttribute("address", userService.getUserAddressTo(authentication));
        return "order_summary";
    }

    @GetMapping("/checkout/{id}")
    String orderSummary(Authentication authentication, @PathVariable Long id) {
//        if (authentication.isAuthenticated() & !orderService.doIhaveAddress(authentication)){
//            return "redirect:/profile/edit";
//        }
        if (!orderService.doIhaveAddress(authentication)){
            return "redirect:/user/profile/add/address";
        }
        orderService.checkoutOrder(authentication, id);
        return "redirect:/";
////        orderService.checkoutOrder(id);
//        return "redirect:checkout/{id}/summery";
    }


    @GetMapping("/remove/{itemId}")
    String removeOrderItem(Authentication authentication, @PathVariable Long itemId, Model model) {
        try {
            basketService.removeItem(itemId);
            model.addAttribute("successMsg", "Account Edit/Delete successfully");
            return "redirect:/orders/order-summary/";
        }catch (Exception ex){
            return "redirect:/orders/order-summary/";
        }
    }

    @GetMapping("/checkout/{id}/summery")
    String orderProcess(Authentication authentication, @PathVariable Long id) {
        orderService.checkoutOrder(authentication, id);
        return "checkout";
    }
}
