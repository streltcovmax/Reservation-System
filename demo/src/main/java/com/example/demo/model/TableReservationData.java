package com.example.demo.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TableReservationData {

    private LocalDateTime dateTime;
    private int numberOfPeople;
    private int tableNumber;

}
