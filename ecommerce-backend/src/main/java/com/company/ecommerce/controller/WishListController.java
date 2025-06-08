package com.company.ecommerce.controller;

import com.company.ecommerce.config.ApiResponse;
import com.company.ecommerce.dto.product.ProductDto;
import com.company.ecommerce.exception.AuthenticationFailException;
import com.company.ecommerce.model.AuthenticationToken;
import com.company.ecommerce.model.Product;
import com.company.ecommerce.model.User;
import com.company.ecommerce.model.WishList;
import com.company.ecommerce.repository.ProductRepository;
import com.company.ecommerce.repository.TokenRepository;
import com.company.ecommerce.service.AuthenticationService;
import com.company.ecommerce.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/wishlist")
public class WishListController {

    @Autowired
    WishListService wishListService;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addWishList(@RequestParam("productId") Integer productId,
                                                   @RequestParam("token") String token) throws AuthenticationFailException {
        System.out.println("Token: " + token);
        // Authenticate token
        authenticationService.authenticate(token);

        // Fetch user and product
        User user = authenticationService.getUser(token);
        Product product = productRepository.getById(productId);

        // Save wishlist
        WishList wishList = new WishList(user, product);
        wishListService.createWishlist(wishList);

        return new ResponseEntity<>(new ApiResponse(true, "Added to wishlist"), HttpStatus.CREATED);
    }


    @GetMapping("/{token}")
    public ResponseEntity<List<ProductDto>> getWishList(@PathVariable("token") String token) throws AuthenticationFailException {
        // first authenticate if the token is valid
        authenticationService.authenticate(token);
        // then fetch the user linked to the token
        User user = authenticationService.getUser(token);
        // first retrieve the wishlist items
        List<WishList> wishLists = wishListService.readWishList(user);

        List<ProductDto> products = new ArrayList<>();
        for (WishList wishList : wishLists) {
            // change each product to product DTO
            products.add(new ProductDto(wishList.getProduct()));
        }
        // send the response to user
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/")
    public AuthenticationToken getAllToken(){
        return tokenRepository.findTokenByToken("9b22ccd0-624e-4a27-8969-ec4154e2f092");
    }
}
