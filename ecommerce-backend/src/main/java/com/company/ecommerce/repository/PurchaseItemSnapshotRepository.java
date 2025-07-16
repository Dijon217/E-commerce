package com.company.ecommerce.repository;

import com.company.ecommerce.model.PurchasedItemSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseItemSnapshotRepository extends JpaRepository<PurchasedItemSnapshot, Integer> {
    boolean existsByName(String name);
    PurchasedItemSnapshot findByName(String name);
}
