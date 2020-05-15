package org.gkk.bioshopapp.web.api.controller;

import org.gkk.bioshopapp.service.model.order.OrderProductCreateServiceModel;
import org.gkk.bioshopapp.service.service.OrderService;
import org.gkk.bioshopapp.service.service.ProductService;
import org.gkk.bioshopapp.web.api.model.order.OrderResponseModel;
import org.gkk.bioshopapp.web.view.model.order.OrderProductModel;
import org.gkk.bioshopapp.web.view.model.product.ProductShoppingCartModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class OrderApiController {

    private final OrderService orderService;
    private final ProductService productService;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderApiController(OrderService orderService, ProductService productService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/api/order/orders")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<OrderResponseModel>> getAllOrders(Principal principal) {
        String username = principal.getName();
        List<OrderResponseModel> list = this.orderService.getAllOrdersByUser(username)
                .stream()
                .map(p -> this.modelMapper.map(p, OrderResponseModel.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/api/order/buy")
    @PreAuthorize("isAuthenticated()")
    public void buy(HttpSession session, HttpServletResponse response) throws Exception {
        String username = session.getAttribute("username").toString();
        HashMap<String, OrderProductModel> cartInSession =
                (HashMap<String, OrderProductModel>) session.getAttribute("cart");

        if (cartInSession.isEmpty()) {
            response.sendRedirect("/order/cart");
            return;
        }

        List<OrderProductCreateServiceModel> ordersService =
                cartInSession.values()
                        .stream()
                        .map(o -> this.modelMapper.map(o, OrderProductCreateServiceModel.class))
                        .collect(Collectors.toList());

        this.orderService.create(ordersService, username);

        cartInSession.clear();
        response.sendRedirect("/order/orders");
    }

    @PostMapping("/api/order/add-to-cart/{id}")
    @PreAuthorize("isAuthenticated()")
    public void addToCart(@PathVariable String id, Integer quantity, HttpSession session, HttpServletResponse response) throws IOException {
        HashMap<String, OrderProductModel> cart = (HashMap<String, OrderProductModel>) session.getAttribute("cart");

        if (!cart.containsKey(id)) {
            ProductShoppingCartModel product =
                    this.modelMapper.map(this.productService.getShoppingCartProductModelById(id),
                            ProductShoppingCartModel.class);
            cart.put(id, new OrderProductModel(product, quantity));
        } else {
            OrderProductModel orderProductModel = cart.get(id);
            orderProductModel.setQuantity(orderProductModel.getQuantity() + quantity);
        }

        response.sendRedirect("/product");
    }

    @PostMapping("/api/order/remove-from-cart/{id}")
    @PreAuthorize("isAuthenticated()")
    public void removeFromCart(@PathVariable String id, HttpSession session, HttpServletResponse response) throws IOException {
        HashMap<String, OrderProductModel> cart = (HashMap<String, OrderProductModel>) session.getAttribute("cart");
        cart.remove(id);

        response.sendRedirect("/order/cart");
    }

    @GetMapping("/api/order/cart")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<OrderProductModel>> getShoppingCart(HttpSession session) {
        HashMap<String, OrderProductModel> cartInSession =
                (HashMap<String, OrderProductModel>) session.getAttribute("cart");

        List<OrderProductModel> cart = new ArrayList<>(cartInSession.values());

        BigDecimal cartTotalPrice = calculateTotalCartPrice(cart);

        HttpHeaders headers = new HttpHeaders();
        headers.add("cartTotalPrice", cartTotalPrice.toString());

        return new ResponseEntity<>(cart, headers, HttpStatus.OK);
    }

    private BigDecimal calculateTotalCartPrice(List<OrderProductModel> cart) {
        return cart.stream()
                .map(op -> {
                            BigDecimal resultPrice;

                            if (op.getProduct().getPromotionalPrice() == null) {
                                resultPrice = op.getProduct().getPrice().multiply(BigDecimal.valueOf(op.getQuantity()));
                            } else {
                                resultPrice = op.getProduct().getPromotionalPrice().multiply(BigDecimal.valueOf(op.getQuantity()));
                            }

                            return resultPrice;
                        }
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
