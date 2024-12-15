package com.example.demo.controller;

import com.example.demo.model.OrderData;
import com.example.demo.service.TableService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ReservationController {

  

    @GetMapping("/reservation")
    public String showReservationForm(Model model) {
        model.addAttribute("orderData", new OrderData());
        return "contact";
    }

    @PostMapping("/reservation")
    public String submitReservationForm(OrderData data, HttpSession session) {
        data.setTableNumber(((OrderData)session.getAttribute("orderData")).getTableNumber());
        data.setNumberOfPeople(((OrderData)session.getAttribute("orderData")).getNumberOfPeople());
        data.setDateTime(((OrderData)session.getAttribute("orderData")).getDateTime());
        session.setAttribute("orderData", data);
        log.info(data.toString());
        return "order";
    }

}
