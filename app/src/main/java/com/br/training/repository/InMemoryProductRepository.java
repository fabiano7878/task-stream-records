package com.br.training.repository;

import java.util.HashMap;
import java.util.Map;

import com.br.training.model.Product;

public class InMemoryProductRepository {

    private final Map<Long, Product> productStorage = new HashMap<>();

    public Map<Long, Product> findAll() {
        return new HashMap<>(productStorage);
    }

    public Product findById(Long id) {
        return productStorage.get(id);
    }

    public void save(Product product) {    	
        if (product != null && product.id() != null) {
            productStorage.put(product.id(), product);
            System.out.println("Product saved successfully.");            
        }        
        System.out.println("Fim save product....");
    }

    public void deleteById(Long id) {
        productStorage.remove(id);
    }
}