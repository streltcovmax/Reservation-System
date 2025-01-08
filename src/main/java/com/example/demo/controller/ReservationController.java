package com.example.demo.controller;

import com.example.demo.model.OrderData;
import com.example.demo.model.TableReservationData;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ReservationController {
  

    @GetMapping("/reservation")
    public String showReservationForm(Model model, HttpSession session) {
        OrderData orderData = new OrderData();
        OrderData sessionOrderData = (OrderData) session.getAttribute("orderData");
        if(sessionOrderData != null){
            orderData = sessionOrderData;
            log.info(orderData.toString());
        }
        model.addAttribute("orderData", orderData);
        return "contact";
    }

    //Не уверен что здесь нужна модель, без нее тоже работало, но иде ругалась
    @PostMapping("/reservation")
    public String submitReservationForm(OrderData data, HttpSession session, Model model) {
        data.setTableNumber(((TableReservationData)session.getAttribute("tableData")).getTableNumber());
        data.setNumberOfPeople(((TableReservationData)session.getAttribute("tableData")).getNumberOfPeople());
        data.setDate(((TableReservationData)session.getAttribute("tableData")).getDateTime().toLocalDate());
        data.setTime(((TableReservationData)session.getAttribute("tableData")).getDateTime().toLocalTime());
        session.setAttribute("orderData", data);
        model.addAttribute("orderData", data);
        log.info(data.toString());
        return "order";
    }

}
