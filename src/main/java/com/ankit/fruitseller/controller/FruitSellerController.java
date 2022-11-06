package com.ankit.fruitseller.controller;

import com.ankit.fruitseller.models.Order;
import com.ankit.fruitseller.models.Product;
import com.ankit.fruitseller.service.FruitSellerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class FruitSellerController {
    @Autowired
    private FruitSellerService service;

    @PostMapping(value = "/order", consumes = "application/json", produces = "application/json")
    ResponseEntity<Order> post(@RequestBody Order order) {
        return new ResponseEntity<>(service.save(order), HttpStatus.CREATED);
    }

    @PostMapping(value = "/product", consumes = "application/json", produces = "application/json")
    ResponseEntity<Product> post(@RequestBody Product product) {
        return new ResponseEntity<>(service.save(product), HttpStatus.CREATED);
    }

    @GetMapping(value = "/product", produces = "application/json")
    ResponseEntity<List<Product>> get() {
        return new ResponseEntity<>(service.getProducts(), HttpStatus.OK);
    }

    @GetMapping(value = "/product/{name}", produces = "application/json")
    ResponseEntity<Product> get(@PathVariable String name) {
        return new ResponseEntity<>(service.get(name), HttpStatus.OK);
    }

    @GetMapping(value = "/order/{orderId}", produces = "application/json")
    ResponseEntity<Order> get(@PathVariable UUID orderId) {
        return new ResponseEntity<>(service.get(orderId), HttpStatus.OK);
    }

    @GetMapping(value = "/order", produces = "application/json")
    ResponseEntity<List<Order>> get(@RequestParam(value = "filter.orderId", required = false) UUID orderId,
                                    @RequestParam(value = "filter.shipment.method", required = false) String shipMethod,
                                    @RequestParam(value = "filter.order.status", required = false) String orderStatus) {
        return new ResponseEntity<>(service.get(orderId, shipMethod, orderStatus), HttpStatus.OK);
    }

    @PutMapping(value = "/product/{name}/reduceCount/{count}")
    void update(@PathVariable String name, @PathVariable int count) {
        service.updateProduct(name, count);
    }

    @PatchMapping(value = "/order/{orderId}", consumes = "application/json", produces = "application/json")
    ResponseEntity<Order> patch(@PathVariable UUID orderId, @RequestBody JsonNode orderPatch) throws JsonPatchException, JsonProcessingException {
        return new ResponseEntity<>(service.patch(orderId, orderPatch), HttpStatus.OK);
    }
}
