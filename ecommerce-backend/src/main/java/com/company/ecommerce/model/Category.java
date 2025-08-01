package com.company.ecommerce.model;

import com.company.ecommerce.dto.category.CategoryDto;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "category_name")
    private @NotBlank String categoryName;

    private @NotBlank String description;
    private @NotBlank String imageUrl;

    public Category(){
    }

    public Category(@NotBlank String categoryName, @NotBlank String description) {
        this.categoryName = categoryName;
        this.description = description;
    }

    public Category(@NotBlank String categoryName, @NotBlank String description, @NotBlank String imageUrl) {
        this.categoryName = categoryName;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public Category(CategoryDto categoryDto) {
        this.categoryName = categoryDto.getCategoryName();
        this.description = categoryDto.getDescription();
//        this.imageUrl = categoryDto.getImageUrl();
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User {category id=" + id +
                ", category name='"
                + categoryName
                + "', description='"
                + description + "'}";
    }
}
