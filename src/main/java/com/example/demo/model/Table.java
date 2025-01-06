package com.example.demo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "Tables")
public class Table {

    @Id
    private int id;
    private int capacity;
    private Long[] ordersId;
}
