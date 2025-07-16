package com.company.ecommerce.repository;

import com.company.ecommerce.model.Product;
import com.company.ecommerce.model.User;
import com.company.ecommerce.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Integer> {
    boolean existsByProduct(Product product);
    List<WishList> findAllByUserOrderByCreatedDateDesc(User user);
    void deleteByProduct(Product product);
}
