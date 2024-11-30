package com.example.demo.controller;

import com.example.demo.model.ReservationData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class ReservationController {

    @GetMapping("")
    public String showReservationForm(Model model) {
        model.addAttribute("reservationData", new ReservationData());
        return "index";
    }

    @PostMapping("")
    public String submitReservationForm(ReservationData data, Model model) {
        // Здесь можно сохранить данные в базу данных или обработать их
//        model.addAttribute("successMessage", "Бронирование успешно отправлено!");
        log.info(String.valueOf(data));
        return "order";
    }
}
