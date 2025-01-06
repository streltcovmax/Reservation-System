package com.example.demo.service;

import com.example.demo.model.OrderData;
import com.example.demo.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public List<OrderData> findByOrderData(OrderData searchData) {
        List<List<OrderData>> matchDataList = new ArrayList<>();
        matchDataList.add(orderRepository.findAll());

        if (searchData.getId() != null) {
            List<OrderData> matchById = orderRepository.findAllById(searchData.getId());
            matchDataList.add(matchById);
        }
        if (searchData.getTableNumber() != null) {
            List<OrderData> matchByTable = orderRepository.findAllByTableNumber(searchData.getTableNumber());
            matchDataList.add(matchByTable);
        }
        if (searchData.getNumberOfPeople() != null) {
            List<OrderData> matchByPeople = orderRepository.findAllByNumberOfPeople(searchData.getNumberOfPeople());
            matchDataList.add(matchByPeople);
        }
        if (searchData.getFullName() != null && !searchData.getFullName().isEmpty()) {
            List<OrderData> matchByName = orderRepository.findAllByFullName(searchData.getFullName());
            matchDataList.add(matchByName);
        }
        if (searchData.getPhoneNumber() != null && !searchData.getPhoneNumber().isEmpty()) {
            List<OrderData> matchByPhone = orderRepository.findAllByPhoneNumber(searchData.getPhoneNumber());
            matchDataList.add(matchByPhone);
        }
        if (searchData.getDate() != null) {
            List<OrderData> matchByDate = orderRepository.findAllByDate(searchData.getDate());
            matchDataList.add(matchByDate);
        }

        List<OrderData> intersection = new ArrayList<>(matchDataList.get(0));

        for (List<OrderData> list: matchDataList) {
            intersection.retainAll(list);
        }

        return intersection;
    }
}
