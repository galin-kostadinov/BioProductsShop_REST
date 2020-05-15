package org.gkk.bioshopapp.web.view.controller;

import org.gkk.bioshopapp.web.annotation.PageTitle;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {

    @GetMapping("/orders")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Orders")
    public ModelAndView getAllOrders() {
        return super.view("order/orders");
    }

    @GetMapping("/cart")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Shopping Cart")
    public ModelAndView getShoppingCart() {
        return super.view("order/shopping-cart");
    }
}
