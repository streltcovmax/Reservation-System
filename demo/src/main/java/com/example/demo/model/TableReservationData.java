package com.example.demo.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TableReservationData {

    private LocalDateTime dateTime;
    private Integer numberOfPeople;
    private int tableNumber;

}
