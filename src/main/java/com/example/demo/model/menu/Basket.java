package com.example.demo.model.menu;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

@Data
@Document(collection = "Baskets")
public class Basket {
    @Id
    private Long orderId;
    private int totalCost;
    private Map<String, Integer> dishes = new HashMap<>();
}
