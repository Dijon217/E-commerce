package com.company.ecommerce.service;

import com.company.ecommerce.model.Category;
import com.company.ecommerce.repository.CategoryRepository;
import com.company.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    UploadToS3Service uploadToS3Service;

    public Category readCategory(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName);
    }

    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    public List<Category> listCategory() {
        return categoryRepository.findAll();
    }

    public Optional<Category> readCategory(Integer categoryID){
        return categoryRepository.findById(categoryID);
    }

    public void updateCategory(Integer categoryID, Category newCategory) {
        Category category = categoryRepository.findById(categoryID).get();

        category.setCategoryName(newCategory.getCategoryName());
        category.setDescription(newCategory.getDescription());
        category.setImageUrl(newCategory.getImageUrl());

        categoryRepository.save(category);
    }

    public boolean deleteCategory(Integer categoryID) {
        Category category = categoryRepository.findById(categoryID).orElse(null);
        if(productRepository.existsByCategoryId(categoryID) || category == null){
            return false;
        }

        uploadToS3Service.deleteFileBucket(category.getImageUrl());
        categoryRepository.deleteById(categoryID);
        return true;
    }
}
