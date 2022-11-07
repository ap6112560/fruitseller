package com.ankit.fruitseller.service;

import com.ankit.fruitseller.models.Item;
import com.ankit.fruitseller.models.Order;
import com.ankit.fruitseller.models.Payment;
import com.ankit.fruitseller.models.Shipment;
import com.ankit.fruitseller.repository.OrderFilterRepository;
import com.ankit.fruitseller.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    OrderFilterRepository orderFilterRepository;
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

    public List<Order> get(UUID orderId, String shipMethod, String orderStatus) {
        List<UUID> ids = orderFilterRepository.getOrderIdsByFilters(orderId, shipMethod, orderStatus);
        return repository.findAllById(ids);
    }
}
