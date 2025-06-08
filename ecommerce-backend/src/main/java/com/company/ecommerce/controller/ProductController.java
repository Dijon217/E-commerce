package com.company.ecommerce.controller;

import com.company.ecommerce.config.ApiResponse;
import com.company.ecommerce.dto.product.ProductDto;
import com.company.ecommerce.exception.ProductNotExistException;
import com.company.ecommerce.model.Category;
import com.company.ecommerce.model.Product;
import com.company.ecommerce.service.CategoryService;
import com.company.ecommerce.service.ProductService;
import com.company.ecommerce.service.UploadToS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private  UploadToS3Service uploadToS3;

    @PostMapping(value = "/add",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> addProduct(@RequestParam("categoryId") Integer categoryId,
                                                  @RequestParam("name") String categoryName,
                                                  @RequestParam("description") String categoryDescription,
                                                  @RequestPart("image") MultipartFile image,
                                                  @RequestParam("price") double categoryPrice) throws IOException {

        String imageLink = uploadToS3.uploadFileToBucket(image);
        ProductDto productDto = new ProductDto(categoryName,imageLink, categoryPrice, categoryDescription, categoryId);
        Optional<Category> optionalCategory = categoryService.readCategory(productDto.getCategoryId());
        if(!optionalCategory.isPresent()) {
            return new ResponseEntity<>(new ApiResponse(false, "Category is Invalid"), HttpStatus.CONFLICT);
        }
        Category category = optionalCategory.get();
        productService.addProduct(productDto, category);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Product has been added"), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductDto>> getProducts(){
        List<ProductDto> productDtos = productService.listProduct();
        return new ResponseEntity<>(productDtos,HttpStatus.OK);
    }

    @PostMapping("/update/{productID}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productID") Integer productID,
                                                     @RequestBody @Valid ProductDto productDto){
        Optional<Category> optionalCategory = categoryService.readCategory(productDto.getCategoryId());
        if(!optionalCategory.isPresent()){
            return new ResponseEntity<>(new ApiResponse(false, "Category is Invalid"), HttpStatus.CONFLICT);
        }
        Category category = optionalCategory.get();
        productService.updateProduct(productID, productDto, category);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Product has been updated"), HttpStatus.CREATED);
    }

    @PostMapping("/delete/{productID}")
    public ResponseEntity<ApiResponse>  deleteProduct(@PathVariable("productID") Integer productID){
        Optional<Product> optionalProduct;
        try{
            optionalProduct = Optional.ofNullable(productService.getProductById(productID));
        } catch (ProductNotExistException e) {
            throw new RuntimeException(e);
        }
        if(optionalProduct.isEmpty()){
            return new ResponseEntity<>(new ApiResponse(false, "Product does not exist"), HttpStatus.CONFLICT);
        }
        productService.delete(productID);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Product has been deleted"), HttpStatus.CREATED);
    }


}
