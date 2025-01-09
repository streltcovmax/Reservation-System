package com.example.demo.model.menu;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@Document(collection = "Dishes")
public class Dish {

    @Id
    private String name;
    private String description;
    private String groupName;
    private Map<String, Integer> ingredientsAmounts;
    private boolean isAvailable;
    private int cost;

}
