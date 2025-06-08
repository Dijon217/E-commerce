package com.company.ecommerce.service;

import com.company.ecommerce.exception.ProductNotExistException;
import com.company.ecommerce.dto.product.ProductDto;
import com.company.ecommerce.model.Category;
import com.company.ecommerce.model.Product;
import com.company.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public void addProduct(ProductDto productDto, Category category) {
        Product product = getProductFromDto(productDto, category);
        productRepository.save(product);
    }

    private Product getProductFromDto(ProductDto productDto, Category category) {
        System.out.println("Product DTO " + productDto.toString());
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImageURL(productDto.getImageUrl());
        product.setCategory(category);
        return product;
    }

    public List<ProductDto> listProduct() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = new ArrayList<>();

        for(Product product : products){
            productDtos.add(new ProductDto(product));
        }
        return productDtos;
    }

    public void updateProduct(Integer productID, ProductDto productDto, Category category) {
        Product product = getProductFromDto(productDto, category);
        product.setId(productID);
        productRepository.save(product);
    }

    public Product getProductById(Integer productId) throws ProductNotExistException {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty())
            throw new ProductNotExistException("Product id is invalid " + productId);
        return optionalProduct.get();
    }

    public void delete(Integer productID){
        productRepository.deleteById(productID);
    }
}
