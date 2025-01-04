package com.example.demo.controller;

import com.example.demo.model.OrderData;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.service.IncrementService;
import com.example.demo.service.TableService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepo;
    private final IncrementService incrementService;
    private final TableService tableService;


    @GetMapping("/processedOrder")
    public String saveOrder(HttpSession session, Model model){
        OrderData data = (OrderData) session.getAttribute("orderData");
        if(data == null) {
            log.info("order data is null");
            return "redirect:/";
        }

        data.setId(incrementService.generateCounter(OrderData.COUNTER_NAME));
        model.addAttribute("orderData", data);
        log.info(String.valueOf(data));
        orderRepo.save(data);
        tableService.addOrderToTable(data.getId(), data.getTableNumber());
        session.invalidate();
        return "result";
    }
}
