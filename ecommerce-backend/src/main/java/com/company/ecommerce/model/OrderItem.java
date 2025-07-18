package com.company.ecommerce.model;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Optional;

@Entity
@Table(name = "orderItems")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "quantity")
    private @NotNull int quantity;

    @Column(name = "price")
    private @NotNull double price;

    @Column(name = "created_date")
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "purchased_Item_id", referencedColumnName = "id")
    private PurchasedItemSnapshot purchasedItemSnapshot;


    public OrderItem(){
    }

    public OrderItem(Order order, @NotNull PurchasedItemSnapshot purchasedItemSnapshot, @NotNull int quantity, @NotNull double price){
        this.purchasedItemSnapshot = purchasedItemSnapshot;
        this.quantity = quantity;
        this.price = price;
        this.order = order;
        this.createdDate = new Date();
    }

    public OrderItem(Date createdDate, double price, PurchasedItemSnapshot purchasedItemSnapshot, int quantity, Order order) {
        this.quantity = quantity;
        this.price = price;
        this.createdDate = createdDate;
        this.order = order;
        this.purchasedItemSnapshot = purchasedItemSnapshot;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public PurchasedItemSnapshot getPurchasedItemSnapshot() {
        return purchasedItemSnapshot;
    }

    public void setPurchasedItemSnapshot(PurchasedItemSnapshot purchasedItemSnapshot) {
        this.purchasedItemSnapshot = purchasedItemSnapshot;
    }
}
