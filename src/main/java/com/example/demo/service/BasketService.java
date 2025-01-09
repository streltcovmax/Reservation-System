package com.example.demo.service;

import com.example.demo.model.menu.Basket;
import com.example.demo.model.menu.Dish;
import com.example.demo.repositories.BasketRepository;
import com.example.demo.repositories.DishRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class BasketService {
    private final IngredientService ingredientService;
    private final DishRepository dishRepository;

    public void processBasketIngreds(Basket basket){
        Map<String, Integer> dishesAmounts = basket.getDishes();
        dishesAmounts.forEach((dishName, dishAmount) -> {
            try {
                Map<String, Integer> ingredsAmounts = dishRepository.findByName(dishName).getIngredientsAmounts();
                ingredientService.editAll(ingredsAmounts, dishAmount);
            }
            catch (Exception e){
                log.error("НЕ УДАЛОСЬ ОБРАБОТАТЬ КОРЗИНУ " + e);
            }
        });
    }

    public void removeDish(Basket basket, Dish dish){
        Map<String, Integer> dishes = basket.getDishes();
        dishes.put(dish.getName(), dishes.getOrDefault(dish.getName(), 0) - 1);
        basket.setTotalCost(basket.getTotalCost() - dish.getCost());


    }
}
