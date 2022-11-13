package com.ankit.fruitseller.repository;

import com.ankit.fruitseller.models.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    @EntityGraph(attributePaths = {"shipment", "payment"})
    List<Order> findByOrderIdIn(List<UUID> ids);
}
