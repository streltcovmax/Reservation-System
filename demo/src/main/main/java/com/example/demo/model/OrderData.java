package com.example.demo.model;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Document(collection = "OrderData")
public class OrderData {

    @Transient
    public static final String COUNTER_NAME = "OrderData_counter";

    @Id
    private Long id;

    private LocalDate date;
    private LocalTime time;
    private Integer numberOfPeople;
    private String fullName;
    private String phoneNumber;
    private String comment;
    private boolean orderNow;
    private Integer tableNumber;
}
