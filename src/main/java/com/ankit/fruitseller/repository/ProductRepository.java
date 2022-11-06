package com.ankit.fruitseller.repository;

import com.ankit.fruitseller.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface ProductRepository extends JpaRepository<Product, String> {
    @Modifying
    @Transactional
    @Query(value = "update Product set availableItemCount = availableItemCount - :count where name = :name and " +
            "(availableItemCount - :count) > 0")
    void updateAvailableItemCount(String name, int count);
}
