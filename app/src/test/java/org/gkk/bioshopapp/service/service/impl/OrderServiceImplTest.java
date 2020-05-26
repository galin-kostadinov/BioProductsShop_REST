package org.gkk.bioshopapp.service.service.impl;

import org.gkk.bioshopapp.base.TestBase;
import org.gkk.bioshopapp.data.model.Order;
import org.gkk.bioshopapp.data.model.Product;
import org.gkk.bioshopapp.data.model.User;
import org.gkk.bioshopapp.data.repository.OrderRepository;
import org.gkk.bioshopapp.service.model.order.OrderProductCreateServiceModel;
import org.gkk.bioshopapp.service.model.order.OrderServiceModel;
import org.gkk.bioshopapp.service.model.product.ProductIdServiceModel;
import org.gkk.bioshopapp.service.service.OrderService;
import org.gkk.bioshopapp.service.service.ProductService;
import org.gkk.bioshopapp.service.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderServiceImplTest extends TestBase {

    @MockBean
    OrderRepository orderRepository;

    @MockBean
    UserService userService;

    @MockBean
    ProductService productService;

    @Autowired
    OrderService orderService;

    @Test
    public void create_whenOrderProductsExist_shouldCreateOrderWithOrderedProducts() throws Exception {
        String productFirstId = "productOneId";
        Integer productFirstQuantity = 10;

        OrderProductCreateServiceModel orderProductFirst = new OrderProductCreateServiceModel();
        ProductIdServiceModel productFirstServiceModel = new ProductIdServiceModel();
        productFirstServiceModel.setId(productFirstId);
        orderProductFirst.setProduct(productFirstServiceModel);
        orderProductFirst.setQuantity(productFirstQuantity);

        List<OrderProductCreateServiceModel> orderProducts = new ArrayList<>();
        orderProducts.add(orderProductFirst);

        String username = "ivanov";

        Product product = new Product();
        product.setId(productFirstId);
        Mockito.when(productService.getProduct(productFirstId)).thenReturn(product);

        orderService.create(orderProducts, username);

        ArgumentCaptor<Order> argumentOrder = ArgumentCaptor.forClass(Order.class);
        Mockito.verify(orderRepository).saveAndFlush(argumentOrder.capture());

        Order order = argumentOrder.getValue();

        assertEquals(productFirstId, order.getOrderProducts().get(0).getProduct().getId());
        assertEquals(productFirstQuantity, order.getOrderProducts().get(0).getQuantity());
    }


    @Test
    public void create_whenBuyerExist_shouldCreateOrderWithGivenBuyer() throws Exception {
        String username = "ivanov";
        User buyer = new User();
        buyer.setUsername(username);

        Mockito.when(userService.getUserEntityByUsername(username)).thenReturn(buyer);

        orderService.create(new ArrayList<>(), username);

        ArgumentCaptor<Order> argumentOrder = ArgumentCaptor.forClass(Order.class);
        Mockito.verify(orderRepository).saveAndFlush(argumentOrder.capture());

        Order order = argumentOrder.getValue();

        assertEquals(order.getBuyer().getUsername(), buyer.getUsername());
    }

    @Test
    public void getAllOrdersByUser_whenBuyerExist_shouldReturnAllOrders() {
        String buyerUsername = "ivanov";

        String orderFirstId = "orderFirstId";
        Order orderFirst = new Order();
        orderFirst.setId(orderFirstId);

        String orderSecondId = "orderSecondId";
        Order orderSecond = new Order();
        orderSecond.setId(orderSecondId);

        List<Order> orders = new ArrayList<>();
        orders.add(orderFirst);
        orders.add(orderSecond);

        Mockito.when(this.orderRepository.findAllByUsername(buyerUsername)).thenReturn(orders);

        List<OrderServiceModel> resultOrderList = orderService.getAllOrdersByUser(buyerUsername);

        assertEquals(2, resultOrderList.size());
        assertEquals(orderFirstId, resultOrderList.get(0).getId());
        assertEquals(orderSecondId, resultOrderList.get(1).getId());
    }

    @Test
    public void getAllOrdersByUser_whenBuyerDoesNotExist_shouldReturnEmptyList() {
        String buyerUsername = "ivanov";

        List<OrderServiceModel> resultOrderList = orderService.getAllOrdersByUser(buyerUsername);

        assertTrue(resultOrderList.isEmpty());
    }
}