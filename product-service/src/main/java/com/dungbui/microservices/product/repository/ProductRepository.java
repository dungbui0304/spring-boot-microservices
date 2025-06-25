package com.dungbui.microservices.product.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.dungbui.microservices.product.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
    

}
