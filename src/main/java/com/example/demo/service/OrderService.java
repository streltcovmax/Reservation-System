package com.example.demo.service;

import com.example.demo.model.OrderData;
import com.example.demo.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.TabExpander;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final TableService tableService;

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
            List<OrderData> matchByName = orderRepository.findAllByFullNameContains(searchData.getFullName());
            matchDataList.add(matchByName);
        }
        if (searchData.getPhoneNumber() != null && !searchData.getPhoneNumber().isEmpty()) {
            List<OrderData> matchByPhone = orderRepository.findAllByPhoneNumberContains(searchData.getPhoneNumber());
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

    public void deleteById(Long id){
        tableService.deleteOrderFromTable(id);
        orderRepository.deleteById(id);
    }
}
