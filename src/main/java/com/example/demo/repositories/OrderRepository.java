package com.example.demo.repositories;

import com.example.demo.model.OrderData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends MongoRepository<OrderData, Long> {
    OrderData findOrderDataById(Long id);

    List<OrderData> findAllByFullName(String fullName);
    List<OrderData> findAllByPhoneNumber(String phone);
    List<OrderData> findAllByTableNumber(int number);
    List<OrderData> findAllByNumberOfPeople(int number);
    List<OrderData> findAllById(Long id);
    List<OrderData> findAllByDate(LocalDate date);

}
