package com.example.demo.repositories;

import com.example.demo.model.menu.Basket;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BasketRepository extends MongoRepository<Basket, Long> {
}
