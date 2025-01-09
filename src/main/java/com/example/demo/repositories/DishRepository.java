package com.example.demo.repositories;

import com.example.demo.model.menu.Dish;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DishRepository extends MongoRepository<Dish, String> {

    List<Dish> findAllByGroupName(String groupName);
    Dish findByName(String name);
}
