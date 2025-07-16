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
import com.company.ecommerce.repository.WishListRepository;
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
    WishListRepository wishListRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addWishList(@RequestParam("productId") Integer productId,
                                                   @RequestParam("token") String token) throws AuthenticationFailException {
        authenticationService.authenticate(token);

        User user = authenticationService.getUser(token);
        Product product = productRepository.getById(productId);

        WishList wishList = new WishList(user, product);
        wishListService.createWishlist(wishList);

        return new ResponseEntity<>(new ApiResponse(true, "Added to wishlist"), HttpStatus.CREATED);
    }


    @GetMapping("/{token}")
    public ResponseEntity<List<ProductDto>> getWishList(@PathVariable("token") String token) throws AuthenticationFailException {
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);
        List<WishList> wishLists = wishListService.readWishList(user);

        List<ProductDto> products = new ArrayList<>();
        for (WishList wishList : wishLists) {
            ProductDto productDto = new ProductDto(wishList.getProduct());
            productDto.setWishlistID(wishList.getId());
            products.add(productDto);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/")
    public AuthenticationToken getAllToken(){
        return tokenRepository.findTokenByToken("9b22ccd0-624e-4a27-8969-ec4154e2f092");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteWishListItem(@RequestParam("wishListProductId") Integer wishListId,
                                                   @RequestParam("token") String token) throws AuthenticationFailException {
        authenticationService.authenticate(token);
        if(wishListRepository.existsById(wishListId)){
            wishListService.removeItemFromList(wishListId);
            return new ResponseEntity<>(new ApiResponse(true, "WishList product removed succesfully"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse(true, "WishList Product does not exist"), HttpStatus.BAD_REQUEST);
    }
}
