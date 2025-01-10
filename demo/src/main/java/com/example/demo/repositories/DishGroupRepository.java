package com.example.demo.repositories;

import com.example.demo.model.menu.DishGroup;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DishGroupRepository extends MongoRepository<DishGroup, String> {

}
