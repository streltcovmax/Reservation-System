package com.example.demo.model;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "OrderData")
public class OrderData {

    @Transient
    public static final String COUNTER_NAME = "OrderData_counter";

    @Id
    private long id;

    private LocalDateTime dateTime;
    private int numberOfPeople = 2;
    private String fullName;
    private String phoneNumber;
    private String comment;
    private boolean orderNow;
    private int tableNumber;
}
