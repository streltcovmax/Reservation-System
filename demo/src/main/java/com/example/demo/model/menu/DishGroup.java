package com.example.demo.model.menu;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document(collection = "DishGroups")
public class DishGroup {
    @Id
    private String name;
    private String description;
}
