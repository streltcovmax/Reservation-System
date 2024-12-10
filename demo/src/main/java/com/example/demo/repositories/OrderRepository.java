package com.example.demo.repositories;

import com.example.demo.model.OrderData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<OrderData, String> {
    OrderData findOrderDataById(long id);
}
