package com.example.demo.controller;

import com.example.demo.model.menu.Basket;
import com.example.demo.model.menu.Dish;
import com.example.demo.model.menu.DishGroup;
import com.example.demo.model.menu.Ingredient;
import com.example.demo.repositories.DishGroupRepository;
import com.example.demo.repositories.DishRepository;
import com.example.demo.service.BasketService;
import com.example.demo.service.IngredientService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MenuController {

    private final DishGroupRepository dishGroupRepository;
    private final DishRepository dishRepository;
    private final IngredientService ingredientService;

    @GetMapping("/menu")
    public String showMenu(Model model, HttpSession session){
        ingredientService.updateAllDishesStatus();
        List<DishGroup> dishGroups = dishGroupRepository.findAll();
        List<Dish> dishes = dishRepository.findAll();
        model.addAttribute("dishGroups", dishGroups);
        model.addAttribute("dishes", dishes);
        Basket basket = (Basket) session.getAttribute("basket");
        if(basket == null){
            basket = new Basket();
        }
        model.addAttribute("basket", basket);
        session.setAttribute("basket", basket);
        return "menu";
    }

    @PostMapping("/menu.addDish")
    public ResponseEntity<String> addToBasket(@RequestBody String dishName, HttpSession session){
        log.info("U WANNA ADDD " + dishName);
        Basket basket = (Basket) session.getAttribute("basket");
        Dish dish = dishRepository.findByName(dishName);

        Map<String, Integer> dishes = basket.getDishes();
        dishes.put(dishName, dishes.getOrDefault(dishName, 0) + 1);
        basket.setTotalCost(basket.getTotalCost() + dish.getCost());
        session.setAttribute("basket", basket);
        log.info(basket.getDishes().toString());
        return ResponseEntity.ok("okeoke");
    }

    @PostMapping("/menu.removeDish")
    public ResponseEntity<String> removeFromBasket(@RequestBody String dishName, HttpSession session){
        log.info("U WANNA REMOVE " + dishName);
        Basket basket = (Basket) session.getAttribute("basket");
        Dish dish = dishRepository.findByName(dishName);

        Map<String, Integer> dishesMap = basket.getDishes();

        dishesMap.put(dishName, dishesMap.getOrDefault(dishName, 0) - 1);
        if(dishesMap.get(dishName) <= 0){
            dishesMap.remove(dishName);
        }
        basket.setTotalCost(basket.getTotalCost() - dish.getCost());
        log.info(basket.getDishes().toString());
        session.setAttribute("basket", basket);
        return ResponseEntity.ok("okeoke");
    }

    @GetMapping("/menu/basket")
    public ResponseEntity<Basket> basket(HttpSession httpSession){
        Basket basket = (Basket) httpSession.getAttribute("basket");
        return ResponseEntity.ok((Basket) httpSession.getAttribute("basket"));
    }

    @PostMapping("/menu/basket.save")
    public ResponseEntity<String> saveBasket(HttpSession session){
        Basket basket = (Basket) session.getAttribute("basket");
        log.info("BASET" + basket);
        return ResponseEntity.ok("bebe");
    }


}
