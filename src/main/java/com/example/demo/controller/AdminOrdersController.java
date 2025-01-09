package com.example.demo.controller;

import com.example.demo.model.AdminData;
import com.example.demo.model.OrderData;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.service.OrderService;
import com.example.demo.service.TableService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AdminOrdersController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final TableService tableService;


    @PostMapping("/admin/orders.search")
    public String searchOrders(OrderData inputData, Model model, HttpSession session){
        model.addAttribute("adminData", session.getAttribute("adminData"));
        model.addAttribute("searchData", inputData);
        List<OrderData> foundOrders = orderService.findByOrderData(inputData);
        log.info(inputData.toString());
        log.info(foundOrders.toString());
        model.addAttribute("foundOrders", foundOrders);
        if(foundOrders.isEmpty()) model.addAttribute("searchInfoMessage", "Ниче не найдено!");
        return "adminOrders";
    }

    @DeleteMapping("/admin/orders/delete/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long orderId){
        log.info(orderId.toString());
        try {
            orderService.deleteById(orderId);
            return ResponseEntity.ok("Order deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete the order");
        }
    }

    @DeleteMapping("/admin/orders/tableDelete/{orderId}")
    public ResponseEntity<String> deleteOrderFromTable(@PathVariable Long orderId){
        try {
            tableService.deleteOrderFromTable(orderId);
            return ResponseEntity.ok("Order from table deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete the order");
        }
    }

    @PostMapping("/admin/orders/update/{orderId}")
    public ResponseEntity<String> updateOrder(@PathVariable Long orderId, @RequestBody OrderData updatedData) {
        log.info("SMT CHANGED: ");
        OrderData oldData = orderRepository.findOrderDataById(orderId);
        log.info("OLD: " + oldData);
        log.info("NEW: " + updatedData.toString());
        updatedData.setId(orderId);
        if(updatedData.getTableNumber() == null || updatedData.getTableNumber() == 0){
            updatedData.setTableNumber(oldData.getTableNumber());
        }
        tableService.addOrderToTable(orderId, updatedData.getTableNumber());
        orderRepository.save(updatedData);
        return ResponseEntity.ok("bebe");
    }
}