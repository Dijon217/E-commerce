package com.company.ecommerce.controller;

import com.company.ecommerce.config.ApiResponse;
import com.company.ecommerce.dto.category.CategoryDto;
import com.company.ecommerce.model.Category;
import com.company.ecommerce.service.CategoryService;
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
import java.util.Objects;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UploadToS3Service uploadFileToS3;

    @PostMapping(value = "/create",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse>
    createCategory(@RequestPart("categoryName") String categoryName,
                   @RequestPart("description") String description,
                   @RequestPart("image") MultipartFile imageFile) throws IOException {

        String imageLink = uploadFileToS3.uploadFileToBucket(imageFile);
        Category category = new Category(categoryName, description, imageLink);
        if(Objects.nonNull(categoryService.readCategory(category.getCategoryName()))){
            return new ResponseEntity<ApiResponse>(new ApiResponse(false,"Category already exist"), HttpStatus.CONFLICT);
        }
        categoryService.createCategory(category);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true,"Category created successfully"), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<Category>> getCategories(){
        List<Category> body = categoryService.listCategory();
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @PostMapping("/update/{categoryID}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable("categoryID") Integer categoryID, @Valid @RequestBody Category category){
        if(Objects.nonNull(categoryService.readCategory(categoryID))){
            categoryService.updateCategory(categoryID, category);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Updated the category"), HttpStatus.OK);
        }
        return new ResponseEntity<ApiResponse>(new ApiResponse(false,"Category does not exist"), HttpStatus.NOT_EXTENDED);
    }

    @PostMapping("/delete/{categoryID}")
    public  ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryID") Integer categoryID){
        if(Objects.nonNull(categoryService.readCategory(categoryID))){
            categoryService.deleteCategory(categoryID);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true,"Category Deleted"), HttpStatus.OK);
        }
        return new ResponseEntity<ApiResponse>(new ApiResponse(false,"Category does not exist"), HttpStatus.NOT_EXTENDED);
    }

}
