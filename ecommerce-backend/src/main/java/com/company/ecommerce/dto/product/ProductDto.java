package com.company.ecommerce.dto.product;

import com.company.ecommerce.model.Product;

import javax.validation.constraints.NotNull;

public class ProductDto {
    private Integer id;
    private @NotNull String name;
    private @NotNull String imageURL;
    private @NotNull double price;
    private @NotNull String description;
    private @NotNull Integer categoryId;

    public ProductDto(@NotNull String name, @NotNull String imageURL, @NotNull double price, @NotNull String description, @NotNull Integer categoryId) {
        this.name = name;
        this.imageURL = imageURL;
        this.price = price;
        this.description = description;
        this.categoryId = categoryId;
    }

    public ProductDto(Product product){
        this.setId(product.getId());
        this.setName(product.getName());
        this.setImageUrl(product.getImageURL());
        this.setPrice(product.getPrice());
        this.setDescription(product.getDescription());
        this.setCategoryId(product.getCategory().getId());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageURL;
    }

    public void setImageUrl(String imageUrl) {
        this.imageURL = imageUrl;
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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageURL + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", categoryId=" + categoryId +
                '}';
    }
}

