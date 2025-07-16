package com.company.ecommerce.repository;

import com.company.ecommerce.model.Cart;
import com.company.ecommerce.model.Product;
import com.company.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findAllByUserOrderByCreatedDateDesc(User user);
    void deleteById(int cartId);
    boolean existsByProduct(Product product);
    void deleteByProduct(Product product);
}