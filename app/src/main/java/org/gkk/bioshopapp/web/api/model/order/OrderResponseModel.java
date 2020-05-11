package org.gkk.bioshopapp.web.api.model.order;

import org.gkk.bioshopapp.data.model.OrderStatus;
import org.gkk.bioshopapp.service.model.order.OrderProductServiceModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderResponseModel {
    private String id;

    private LocalDateTime dateCreated;

    private OrderStatus status;

    private List<OrderProductServiceModel> orderProducts;

    private BigDecimal totalPrice;

    public OrderResponseModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<OrderProductServiceModel> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProductServiceModel> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
