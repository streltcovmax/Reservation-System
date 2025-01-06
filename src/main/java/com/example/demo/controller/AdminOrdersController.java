package com.example.demo.controller;

import com.example.demo.model.OrderData;
import com.example.demo.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminOrdersController {

    private final OrderRepository orderRepository;

//    @PostMapping("/admin.ordersSearch")
//    public ResponseEntity<List<OrderData>> search(@RequestBody OrderData inputData){
//        List<OrderData> foundData = orderRepository.findAllByFullName(inputData.getFullName());
////        log.info(inputData.toString());
//    //через js фетч по онклику
//        return ResponseEntity.ok(foundData);
//    }


}
