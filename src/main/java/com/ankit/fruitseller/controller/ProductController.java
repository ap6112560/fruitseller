package com.ankit.fruitseller.controller;

import com.ankit.fruitseller.models.Product;
import com.ankit.fruitseller.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductService service;

    @PostMapping(value = "/product", consumes = "application/json", produces = "application/json")
    ResponseEntity<Product> post(@RequestBody Product product) {
        return new ResponseEntity<>(service.save(product), HttpStatus.CREATED);
    }

    @PutMapping(value = "/product/{name}/reduceCount/{count}")
    void update(@PathVariable String name, @PathVariable int count) {
        service.updateProduct(name, count);
    }

    @GetMapping(value = "/product/{name}", produces = "application/json")
    ResponseEntity<Product> get(@PathVariable String name) {
        return new ResponseEntity<>(service.get(name), HttpStatus.OK);
    }

    @GetMapping(value = "/product", produces = "application/json")
    ResponseEntity<List<Product>> get() {
        return new ResponseEntity<>(service.getProducts(), HttpStatus.OK);
    }
}
