package com.company.ecommerce.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class PurchasedItemSnapshot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private @NotNull String name;
    private @NotNull String imageURL;
    private @NotNull double price;
    private @NotNull String description;

    public PurchasedItemSnapshot() {
    }

    public PurchasedItemSnapshot(String name, String imageURL, double price, String description) {
        this.name = name;
        this.imageURL = imageURL;
        this.price = price;
        this.description = description;
    }

    public PurchasedItemSnapshot(Product product){
        this.name = product.getName();
        this.imageURL = product.getImageURL();
        this.price = product.getPrice();
        this.description = product.getDescription();
    }


    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
