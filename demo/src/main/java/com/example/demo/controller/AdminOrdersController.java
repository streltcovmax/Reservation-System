package com.example.demo.controller;

import com.example.demo.model.OrderData;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.service.OrderService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AdminOrdersController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;

    @PostMapping("/admin/orders.search")
    public String searchOrders(OrderData inputData, Model model, HttpSession session){
        model.addAttribute("adminData", session.getAttribute("adminData"));
        model.addAttribute("searchData", inputData);
        List<OrderData> foundOrders = orderService.findByOrderData(inputData);
        log.info(inputData.toString());
        log.info(foundOrders.toString());
        model.addAttribute("foundOrders", foundOrders);
        if(foundOrders.isEmpty()) model.addAttribute("searchInfoMessage", "Ниче не найдено!");
        return "admin";
    }

    @DeleteMapping("/admin/orders/delete/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long orderId){
        log.info(orderId.toString());
        try {
            orderRepository.deleteById(orderId);
            return ResponseEntity.ok("Order deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete the order");
        }
    }

    @PostMapping("/admin/orders/update/{orderId}")
    public ResponseEntity<String> updateOrder(@PathVariable Long orderId, @RequestBody OrderData updatedData) {
        if(orderRepository.findById(orderId).toString().equals(updatedData.toString())){
            log.info("NOTHING CHANGED");
        }
        else {
            log.info("SMT CHANGED: ");
            log.info("OLD: " + orderRepository.findById(orderId).toString());
            log.info("NEW: " + updatedData.toString());
        }
        return ResponseEntity.ok("bebe");
    }
}