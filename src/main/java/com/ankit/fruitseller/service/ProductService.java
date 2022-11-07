package com.ankit.fruitseller.service;

import com.ankit.fruitseller.models.Product;
import com.ankit.fruitseller.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void updateProduct(String name, int count) {
        productRepository.updateAvailableItemCount(name, count);
    }

    public Product get(String name) {
        return productRepository.findById(name).get();
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }
}
