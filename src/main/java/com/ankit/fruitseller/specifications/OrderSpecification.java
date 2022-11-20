package com.ankit.fruitseller.specifications;

import com.ankit.fruitseller.enums.OrderStatus;
import com.ankit.fruitseller.models.Order;
import com.ankit.fruitseller.models.Shipment;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import java.util.UUID;

public class OrderSpecification {
    public static Specification<Order> getOrdersByShipmentMethodSpec(String sMethod) {
        return (root, query, criteriaBuilder) -> {
            Join<Order, Shipment> join = root.join("shipment");
            return criteriaBuilder.equal(join.get("shipmentMethod"), sMethod);
        };
    }

    public static Specification<Order> getOrdersByStatusSpec(OrderStatus status) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<Order> getOrdersByOrderIdSpec(UUID orderId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("orderId"), orderId);
    }

    public static Specification<Order> getOrdersSpec() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and();
    }
}
