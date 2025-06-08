package com.company.ecommerce.dto.category;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

public class CategoryDto {
    private @NotBlank String categoryName;
    private @NotBlank String description;
    private @NotBlank MultipartFile image;
//    private String imageUrl;

    public CategoryDto(){
    }

    public CategoryDto(String categoryName, String description, MultipartFile image){
        this.categoryName = categoryName;
        this.description = description;
        this.image = image;
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

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
