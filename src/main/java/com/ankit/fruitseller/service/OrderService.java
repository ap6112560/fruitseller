package com.ankit.fruitseller.service;

import com.ankit.fruitseller.enums.OrderStatus;
import com.ankit.fruitseller.mappers.OrderMapper;
import com.ankit.fruitseller.models.Item;
import com.ankit.fruitseller.models.Order;
import com.ankit.fruitseller.models.Payment;
import com.ankit.fruitseller.models.Shipment;
import com.ankit.fruitseller.models.projections.OrderView;
import com.ankit.fruitseller.repository.OrderRepository;
import com.ankit.fruitseller.specifications.OrderSpecification;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    private OrderRepository repository;

    public Order save(Order order) {
        UUID orderId = order.getOrderId();
        List<Item> items = order.getItems();
        Payment payment = order.getPayment();
        Shipment shipment = order.getShipment();

        if (items != null) {
            items.forEach(item -> {
                if (orderId != null) {
                    item.getId().setOrderId(orderId);
                }
                item.setOrder(order);
            });
        }
        if (payment != null) {
            if (orderId != null) {
                payment.setOrderId(orderId);
            }
            payment.setOrder(order);
        }
        if (shipment != null) {
            if (orderId != null) {
                shipment.setOrderId(orderId);
            }
            shipment.setOrder(order);
        }
        return repository.save(order);
    }

    public Order patch(UUID orderId, JsonNode orderPatch) throws JsonPatchException, JsonProcessingException {
        Order orderFromDB = repository.findById(orderId).get();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = JsonMergePatch.fromJson(orderPatch).apply(mapper.valueToTree(orderFromDB));
        Order orderToSave = mapper.treeToValue(node, Order.class);
        return save(orderToSave);
    }

    public Order get(UUID orderId) {
        return repository.findById(orderId).get();
    }

    public List<OrderView> get(UUID orderId, String shipMethod, OrderStatus orderStatus, int pageSize, int pageNo) {
        Specification<Order> specification = OrderSpecification.getOrdersSpec();

        if (orderId != null) {
            specification = specification.and(OrderSpecification.getOrdersByOrderIdSpec(orderId));
        }
        if (orderStatus != null) {
            specification = specification.and(OrderSpecification.getOrdersByStatusSpec(orderStatus));
        }
        if (shipMethod != null) {
            specification = specification.and(OrderSpecification.getOrdersByShipmentMethodSpec(shipMethod));
        }
        Pageable page = PageRequest.of(pageNo - 1, pageSize, Sort.by(Sort.Direction.ASC, "orderDate"));
        List<Order> orders = repository.findAll(specification, page).getContent();
        return OrderMapper.INSTANCE.map(orders);
    }
}
