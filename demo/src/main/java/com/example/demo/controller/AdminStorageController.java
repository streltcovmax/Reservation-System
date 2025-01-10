package com.example.demo.controller;

import com.example.demo.model.OrderData;
import com.example.demo.model.menu.Ingredient;
import com.example.demo.repositories.IngredientRepository;
import com.example.demo.service.IngredientService;
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

@Slf4j
@Controller
@RequiredArgsConstructor
public class AdminStorageController {

    private final IngredientRepository ingredientRepository;
    private final IngredientService ingredientService;

    @PostMapping("/admin/storage.search")
    public String searchIngredients(Ingredient inputData, Model model, HttpSession session){
        model.addAttribute("adminData", session.getAttribute("adminData"));
        model.addAttribute("searchData", inputData);
        List<Ingredient> foundData = ingredientService.findIngredientByData(inputData);
        model.addAttribute("foundData", foundData);
        if(foundData.isEmpty()) model.addAttribute("searchInfoMessage", "Ниче не найдено!");
        return "adminStorage";
    }

    @PostMapping("/admin/storage.edit")
    public ResponseEntity<String> editIngredient(@RequestBody Ingredient inputData, Model model){
        try{
            ingredientRepository.save(inputData);
            ingredientService.updateAllDishesStatus();

        }catch (Exception e){
            log.error("НЕ УДАЛОСЬ СОХРАНИТЬ ДАННЫЕ " + inputData);
        }
        return ResponseEntity.ok("Saved " + inputData);
    }
}