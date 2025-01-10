package com.example.demo.repositories;

import com.example.demo.model.menu.Ingredient;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IngredientRepository extends MongoRepository<Ingredient, String> {

    Ingredient findByName(String name);
    List<Ingredient> findAllByAmount(Integer amount);

    List<Ingredient> findAllByNameContains(String name);
}
