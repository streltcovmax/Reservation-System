package com.example.demo.controller;

import com.example.demo.model.AdminData;
import com.example.demo.model.OrderData;
import com.example.demo.model.menu.Ingredient;
import com.example.demo.repositories.AdminRepository;
import com.example.demo.service.IngredientService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AdminStuffController {

    private final AdminRepository adminRepository;
    private final IngredientService ingredientService;

    @PostMapping("/admin/stuff.search")
    public String searchAdmins(AdminData inputData, Model model, HttpSession session){
        model.addAttribute("adminData", session.getAttribute("adminData"));
        model.addAttribute("searchData", inputData);
        List<AdminData> foundData;
        if(inputData != null && inputData.getName() != null) foundData = adminRepository.findAllByNameContains(inputData.getName());
        else foundData = adminRepository.findAll();
        model.addAttribute("foundData", foundData);
        if(foundData.isEmpty()) model.addAttribute("searchInfoMessage", "Ниче не найдено!");
        return "adminStuff";
    }

    @GetMapping("/admin/stuff.search")
    public String searchAllAdmins(Model model, HttpSession session){
        model.addAttribute("adminData", session.getAttribute("adminData"));
        model.addAttribute("searchData", new AdminData());
        List<AdminData> foundData = adminRepository.findAll();
        model.addAttribute("foundData", foundData);
        if(foundData.isEmpty()) model.addAttribute("searchInfoMessage", "Ниче не найдено!");
        return "adminStuff";
    }

    @PostMapping("/admin/stuff.add")
    public ResponseEntity<String> addAdmin(@RequestBody AdminData inputData){
        try{
            adminRepository.save(inputData);
        }
        catch (Exception e){
            log.error("НЕ УДАЛОСЬ СОХРАНИТЬ ДАННЫЕ " + inputData);
        }
        return ResponseEntity.ok("Saved " + inputData);
    }

    @PostMapping("/admin/stuff.delete")
    public ResponseEntity<String> deleteAdmin(@RequestBody AdminData inputData){
        try{
            adminRepository.delete(adminRepository.findByName(inputData.getName()));
        }
        catch (Exception e){
            log.error("НЕ УДАЛОСЬ УДАЛИТЬ ДАННЫЕ " + inputData);
        }
        return ResponseEntity.ok("Deleted " + inputData);
    }
}