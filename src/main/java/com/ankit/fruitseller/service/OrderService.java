package com.ankit.fruitseller.service;

import com.ankit.fruitseller.models.*;
import com.ankit.fruitseller.repository.CacheRepository;
import com.ankit.fruitseller.repository.OrderFilterRepository;
import com.ankit.fruitseller.repository.OrderRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    OrderFilterRepository orderFilterRepository;
    @Autowired
    private OrderRepository repository;
    @Autowired
    private CacheRepository cacheRepository;
    private ObjectMapper mapper = new ObjectMapper();

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

        Order savedOrder = repository.save(order);
        if (orderId != null) {
            if (cacheRepository.existsById(orderId)) {
                cacheRepository.deleteById(orderId);
            }
        }

        cacheRepository.save(mapToCachedEntity(savedOrder));
        return savedOrder;
    }

    @SneakyThrows
    public Order patch(UUID orderId, JsonNode orderPatch) {
        Order orderFromDB;
        Optional<CachedEntity> cachedOrder = cacheRepository.findById(orderId);
        if (cachedOrder.isPresent()) {
            orderFromDB = mapToOrder(cachedOrder.get());
        } else {
            orderFromDB = repository.findById(orderId).get();
        }
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = JsonMergePatch.fromJson(orderPatch).apply(mapper.valueToTree(orderFromDB));
        Order orderToSave = mapper.treeToValue(node, Order.class);
        return save(orderToSave);
    }

    public Order get(UUID orderId) {
        Order order;
        Optional<CachedEntity> cachedOrder = cacheRepository.findById(orderId);
        if (cachedOrder.isPresent()) {
            return mapToOrder(cachedOrder.get());
        }
        order = repository.findById(orderId).get();
        cacheRepository.save(mapToCachedEntity(order));
        return order;
    }

    public List<Order> get(UUID orderId, String shipMethod, String orderStatus, int pageSize, int pageNo) {
        List<Order> orders = new ArrayList<>(), unCachedOrders = new ArrayList<>(), cachedOrders = new ArrayList<>();

        Set<UUID> ids = orderFilterRepository.getOrderIdsByFilters(orderId, shipMethod, orderStatus, pageSize, pageNo);

        if (!ids.isEmpty()) {
            cachedOrders = cacheRepository.findAllById(ids).stream().map(this::mapToOrder).collect(Collectors.toList());
            Set<UUID> cachedIds = cachedOrders.stream().map(Order::getOrderId).collect(Collectors.toSet());
            ids.removeAll(cachedIds);
        }
        if (!ids.isEmpty()) {
            unCachedOrders = repository.findByOrderIdIn(ids);
            List<CachedEntity> entities = unCachedOrders.stream().map(this::mapToCachedEntity).collect(Collectors.toList());
            cacheRepository.saveAll(entities);
        }
        orders.addAll(cachedOrders);
        orders.addAll(unCachedOrders);
        return orders;
    }

    @SneakyThrows
    private Order mapToOrder(CachedEntity cachedOrder) {
        return mapper.readValue(cachedOrder.getValue(), Order.class);
    }

    @SneakyThrows
    private CachedEntity mapToCachedEntity(Order order) {
        CachedEntity cachedEntity = new CachedEntity();
        cachedEntity.setId(order.getOrderId());
        cachedEntity.setValue(mapper.writeValueAsBytes(order));
        return cachedEntity;
    }
}
