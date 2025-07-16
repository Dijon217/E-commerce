package com.company.ecommerce.service;

import com.company.ecommerce.dto.cart.AddToCartDto;
import com.company.ecommerce.dto.cart.CartDto;
import com.company.ecommerce.dto.cart.CartItemDto;
import com.company.ecommerce.exception.CartItemNotExistException;
import com.company.ecommerce.model.Cart;
import com.company.ecommerce.model.Product;
import com.company.ecommerce.model.User;
import com.company.ecommerce.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;

    public void addToCart(AddToCartDto addToCartDto, Product product, User user) {
        Cart cart = new Cart(product, addToCartDto.getQuantity(), user);
        cartRepository.save(cart);
    }

    public CartDto listCartItems(User user) {
        List<Cart> cartList = cartRepository.findAllByUserOrderByCreatedDateDesc(user);
        List<CartItemDto> cartItems = new ArrayList<>();
        double totalCost = 0;

        for (Cart cart:cartList){
            CartItemDto cartItemDto = new CartItemDto(cart);
            cartItems.add(cartItemDto);
            totalCost += cartItemDto.getProduct().getPrice() * cartItemDto.getQuantity();
        }

        return new CartDto(cartItems,totalCost);
    }

    public void deleteCartItem(int cartItemId, User user) throws CartItemNotExistException {
        Cart cart = cartRepository.findById(cartItemId).orElse(null);
        if(cart == null){
            throw new CartItemNotExistException("cartItemId not valid");
        }
        if (!cart.getUser().equals(user)) {
            throw new CartItemNotExistException("cart item does not belong to user");
        }
        cartRepository.deleteById(cartItemId);
    }
}
