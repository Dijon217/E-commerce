package com.company.ecommerce.service;

import com.company.ecommerce.exception.ProductNotExistException;
import com.company.ecommerce.dto.product.ProductDto;
import com.company.ecommerce.model.Cart;
import com.company.ecommerce.model.Category;
import com.company.ecommerce.model.Product;
import com.company.ecommerce.model.PurchasedItemSnapshot;
import com.company.ecommerce.repository.CartRepository;
import com.company.ecommerce.repository.ProductRepository;
import com.company.ecommerce.repository.PurchaseItemSnapshotRepository;
import com.company.ecommerce.repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    WishListRepository wishListRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    PurchaseItemSnapshotRepository purchaseItemSnapshotRepository;

    @Autowired
    UploadToS3Service uploadToS3Service;

    public void addProduct(ProductDto productDto, Category category) {
        Product product = getProductFromDto(productDto, category);
        productRepository.save(product);
    }

    private Product getProductFromDto(ProductDto productDto, Category category) {
        return new Product(productDto, category);
    }

    public List<ProductDto> listProduct() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDto = new ArrayList<>();

        for (Product product : products) {
            productDto.add(new ProductDto(product));
        }
        return productDto;
    }

    public void updateProduct(Integer productID, ProductDto productDto, Category category) {
        Product product = getProductFromDto(productDto, category);
        product.setId(productID);
        productRepository.save(product);
    }

    public Product getProductById(Integer productId) throws ProductNotExistException {
        Product product = productRepository.findById(productId).orElse(null);
        if(product == null){
            throw new ProductNotExistException("Product id is invalid " + productId);
        }
        return product;
    }

    @Transactional
    public void delete(Integer productID) {
        Product product = productRepository.findById(productID).orElse(null);

        if(product != null){
            if(cartRepository.existsByProduct(product)){
                cartRepository.deleteByProduct(product);
            }
            if(wishListRepository.existsByProduct(product)){
                wishListRepository.deleteByProduct(product);
            }
        }

        if(purchaseItemSnapshotRepository.existsByName(product.getName())){
            productRepository.deleteById(productID);
        }else{
            uploadToS3Service.deleteFileBucket(product.getImageURL());
            productRepository.deleteById(productID);
        }

    }
}
