package com.company.ecommerce.dto.Order;

import com.company.ecommerce.model.Order;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class OrderDto {
    private @NotNull int orderId;
    private @NotNull double totalPrice;
    private @NotNull Date createdDate;
    private @NotNull String imageUrl;
    private @NotNull int totalItems;

    public OrderDto(@NotNull int orderId, @NotNull double totalPrice, @NotNull Date createdDate,
                    @NotNull String imageUrl, @NotNull int totalItems){
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.createdDate = createdDate;
        this.imageUrl = imageUrl;
        this.totalItems = totalItems;
    }

    public OrderDto(@NotNull int orderId, @NotNull double totalPrice,@NotNull int totalItems){
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.totalItems = totalItems;
    }

    public OrderDto(Order order){
        this.orderId = order.getId();
        this.totalPrice = order.getTotalPrice();
        this.createdDate = order.getCreatedDate();
        this.imageUrl = order.getOrderItems().get(0).getProduct().getImageURL();
        this.totalItems = order.getOrderItems().size();
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }
}
