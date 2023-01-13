package com.astra.drunken.controllers;

import com.astra.drunken.services.BasketService;
import com.astra.drunken.services.OrderService;
import com.astra.drunken.services.TemplateHelper;
import com.astra.drunken.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/orders/")
public class OrderController {

    private final OrderService orderService;
    private final BasketService basketService;
    private final UserService userService;
    private final TemplateHelper templateHelper;

    public OrderController(OrderService orderService, BasketService basketService, UserService userService, TemplateHelper templateHelper) {
        this.orderService = orderService;
        this.basketService = basketService;
        this.userService = userService;
        this.templateHelper = templateHelper;
    }

    @GetMapping("/order-summary/")
    String orderSummary(Authentication authentication, Model model) {

        var order = orderService.getOrderByUserAndActive(authentication);
        model.addAttribute("order", order.get());
//        model.addAttribute("address", userService.getUserAddressTo(authentication));
        model.addAttribute("userInfo", userService.getUserTo(authentication));

        templateHelper.defaultTemplateModel(model, authentication);

        return "order_summary";
    }

    @GetMapping("/checkout/{id}")
    String orderSummary(Authentication authentication, @PathVariable Long id, Model model) {
        templateHelper.defaultTemplateModel(model, authentication);
        if (!orderService.doIhaveAnyAddress(authentication)) {
            return "redirect:/user/profile/add/address/delivery";
        }
        if (!orderService.doIhaveBillingAddress(authentication).get()) {
            return "redirect:/user/profile/add/address/billing";

        }
        if (!orderService.doIhaveDeliveryAddress(authentication).get()) {
            return "redirect:/user/profile/add/address/delivery";

        }
        if (model.getAttribute("itemCount") != null &
                ((int) model.getAttribute("itemCount")) == 0) {
            var msg = "PSST!!! your cart is empty";
            model.addAttribute("message", msg);
            return "redirect:/orders/order-summary/";
        }
        orderService.checkoutOrder(authentication, id);
        return "redirect:/";
    }


    @GetMapping("/remove/{itemId}")
    String removeOrderItem(Authentication authentication, @PathVariable Long itemId, Model model) {
        try {
            basketService.removeItem(authentication, itemId);
            templateHelper.defaultTemplateModel(model, authentication);
            return "redirect:/orders/order-summary/?msg=" + "item deleted";
        } catch (Exception ex) {
            templateHelper.defaultTemplateModel(model, authentication);
            return "redirect:/orders/order-summary/";
        }
    }

    @GetMapping("/remove/all")
    String removeOrder(Authentication authentication, Model model) {

        try {
            basketService.removeAllItems(authentication);
            model.addAttribute("message", "cart cleared");
            templateHelper.defaultTemplateModel(model, authentication);
            return "redirect:/orders/order-summary/";
        } catch (Exception ex) {
            templateHelper.defaultTemplateModel(model, authentication);
            return "redirect:/orders/order-summary/";
        }
    }

    @GetMapping("/checkout/{id}/summery")
    String orderProcess(Authentication authentication, @PathVariable Long id, Model model) {
        orderService.checkoutOrder(authentication, id);
        templateHelper.defaultTemplateModel(model, authentication);
        return "checkout";
    }
}
