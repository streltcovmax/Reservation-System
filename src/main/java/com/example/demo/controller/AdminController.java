package com.example.demo.controller;

import com.example.demo.model.AdminData;
import com.example.demo.model.OrderData;
import com.example.demo.repositories.AdminRepository;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.service.OrderService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AdminController {

    private final AdminRepository adminRepository;


    @GetMapping("/adminAut")
    public String adminLogin(Model model) {
        AdminData adminData = new AdminData();
        model.addAttribute("adminData", adminData);
        return "adminAut";
    }

    @GetMapping("/admin")
    public String adminPanel(HttpSession session, Model model) {
        log.info("ADMIN GET MAPPING");
        var isAdmin = session.getAttribute("isAdmin");
        var adminData = session.getAttribute("adminData");
        log.info("from panel enter IS ADMIN: " + isAdmin);
        log.info("from panel enter ADMIN DATA is session: " + adminData);
        if (isAdmin != null && (boolean) isAdmin && adminData != null) {
            model.addAttribute("adminData", adminData);
            log.info("MODEL: " + model.toString());
            log.info("Added that^ admin data to model");
            model.addAttribute("searchData", new OrderData());
            return "admin";
        }
        return "redirect:/adminAut";
    }

    @PostMapping("/admin.check")
    public ResponseEntity<Boolean> checkIfAdmin(@RequestBody AdminData inputData, HttpSession session) {
        AdminData foundData = adminRepository.findByName(inputData.getName());
        boolean isAdmin = foundData != null && inputData.getPassword().equals(foundData.getPassword());
        log.info("from check IS ADMIN: " + isAdmin);
        if (isAdmin) session.setAttribute("adminData", foundData);
        session.setAttribute("isAdmin", isAdmin);
        return ResponseEntity.ok(isAdmin);
    }

    @GetMapping("/admin.logout")
    public String adminLogout(HttpSession session) {
        session.invalidate();
        log.info("SESSION INVALIDATE initiated from AdminPanel");
        return "redirect:/adminAut";
    }

}