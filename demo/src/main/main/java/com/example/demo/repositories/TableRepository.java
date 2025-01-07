package com.example.demo.repositories;

import com.example.demo.model.Table;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;

public interface TableRepository extends MongoRepository<Table, Integer> {

    ArrayList<Table> findAllByCapacityIsGreaterThanEqual(int amount);
    Table findById(int id);

}
