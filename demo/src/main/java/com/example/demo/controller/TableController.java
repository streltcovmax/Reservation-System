package com.example.demo.controller;

import com.example.demo.model.OrderData;
import com.example.demo.service.TableService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class TableController {

    private final TableService tableService;
    @GetMapping("/")
    public String redirectToIndex() {
        return "index"; // Файл index.html будет использоваться как начальная страница
    }

    //Переместишь это потом куда нибудь
    //1
    @GetMapping("/adminAut")
    public String redirectToAdminAut() {
        return "adminAut"; 
    }
    //2
    @GetMapping("/tables")
    public String showTableForm(Model model) {
        model.addAttribute("orderData", new OrderData());
        return "tables"; // Файл tables.html
    }
    //3
    @PostMapping("/adminAut")
    public String authenticateAdmin(@RequestParam String name, @RequestParam String password, Model model) {
        // Проверка имени и пароля
        if ("admin".equals(name) && "admin123".equals(password)) {
            return "redirect:/admin"; // Перенаправляем на страницу администратора
        }
        // Если проверка не удалась, возвращаем ту же страницу с сообщением об ошибке
        model.addAttribute("error", "Неверное имя или пароль");
        return "adminAut"; // Возвращаем обратно страницу авторизации
    }
    //4
    @GetMapping("/admin")
    public String adminPage() {
        return "admin"; // Возвращаем шаблон admin.html
    }



    @PostMapping("/tables")
    public String submitTableForm(HttpSession session, OrderData data){
        int tableNumber = tableService.findTable(data.getDateTime(), data.getNumberOfPeople());
        data.setTableNumber(tableNumber);
        log.info(data.toString());
        if(tableNumber == -1){
            return "notables";
        }
        session.setAttribute("orderData", data);
        return "redirect:/reservation";
    }
}