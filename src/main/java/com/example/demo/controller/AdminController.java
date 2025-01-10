package com.example.demo.controller;

import com.example.demo.model.AdminData;
import com.example.demo.model.OrderData;
import com.example.demo.model.Role;
import com.example.demo.model.menu.Ingredient;
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

import static com.example.demo.model.Role.*;

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
//        var isAdmin = session.getAttribute("isAdmin");
        var adminData = session.getAttribute("adminData");
        if (adminData != null) {
            model.addAttribute("adminData", adminData);
            switch (((AdminData)adminData).getRole()){
                case ORDERS:
                    return "redirect:/admin/orders";
                case STORAGE:
                    return "redirect:/admin/storage";
                case BOSS:
                    return "adminBoss";

            }
            return "adminOrders";
        }
        return "redirect:/adminAut";
    }

    @PostMapping("/admin.check")
    public ResponseEntity<Boolean> checkLoginPass(@RequestBody AdminData inputData, HttpSession session) {
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


    @GetMapping("/admin/orders")
    public String adminOrders(HttpSession session, Model model){
        AdminData adminData = (AdminData) session.getAttribute("adminData");
        if(stillAdmin(adminData, ORDERS)){
            model.addAttribute("searchData", new OrderData());
            model.addAttribute("adminData", adminData);
            return "adminOrders";
        }
        else{
            return "redirect:/admin.logout";
        }

    }

    @GetMapping("/admin/storage")
    public String adminStorage(HttpSession session, Model model){
        AdminData adminData = (AdminData) session.getAttribute("adminData");
        if(stillAdmin(adminData, STORAGE)){
            model.addAttribute("searchData", new Ingredient());
            model.addAttribute("adminData", adminData);
            return "adminStorage";
        }
        else{
            return "redirect:/admin.logout";
        }

    }

    @GetMapping("/admin/stuff")
    public String adminStuff(HttpSession session, Model model){
        AdminData adminData = (AdminData) session.getAttribute("adminData");
        if(stillAdmin(adminData, BOSS)){
            model.addAttribute("searchData", new AdminData());
            model.addAttribute("adminData", adminData);
            return "adminStuff";
        }
        else{
            return "redirect:/admin.logout";
        }

    }

    private boolean stillAdmin(AdminData sessionData, Role role){
        if(sessionData == null) return false;
        return (sessionData.getRole() == role || sessionData.getRole() == BOSS)&& adminRepository.findByName(sessionData.getName()) != null;
    }
}