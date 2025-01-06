package com.example.demo.repositories;

import com.example.demo.model.OrderData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<OrderData, Long> {
    OrderData findOrderDataById(long id);

    List<OrderData> findAllByFullName(String fullName);

}
