package org.gkk.bioshopapp.web.api.controller;

import org.gkk.bioshopapp.service.service.OrderService;
import org.gkk.bioshopapp.web.api.model.order.OrderResponseModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class OrderApiController {

    private final OrderService orderService;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderApiController(OrderService orderService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/api/order/orders")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<OrderResponseModel>> getAllOrders(Principal principal) {
        String username = principal.getName();
        List<OrderResponseModel> list= this.orderService.getAllOrdersByUser(username)
                .stream()
                .map(p -> this.modelMapper.map(p, OrderResponseModel.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
