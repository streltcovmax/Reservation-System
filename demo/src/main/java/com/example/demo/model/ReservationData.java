package com.example.demo.model;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReservationData {

    private LocalDateTime dateTime;
    private int numberOfPeople = 2;
    private String fullName;
    private String phoneNumber;
    private String comment;
    private boolean orderNow;
    private int tableNumber; //??

}
