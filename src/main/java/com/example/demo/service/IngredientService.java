package com.example.demo.service;

import com.example.demo.model.OrderData;
import com.example.demo.model.menu.Dish;
import com.example.demo.model.menu.Ingredient;
import com.example.demo.repositories.DishRepository;
import com.example.demo.repositories.IngredientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final DishRepository dishRepository;

    public List<Ingredient> findIngredientByData(Ingredient searchData){
        List<List<Ingredient>> matchDataList = new ArrayList<>();
        matchDataList.add(ingredientRepository.findAll());

        if(searchData.getName() != null && !searchData.getName().isEmpty()){
            List<Ingredient> matchByName = ingredientRepository.findAllByNameContains(searchData.getName());
            matchDataList.add(matchByName);
        }
        if(searchData.getAmount() != null){
            List<Ingredient> matchByAmount = ingredientRepository.findAllByAmount(searchData.getAmount());
            matchDataList.add(matchByAmount);
        }

        List<Ingredient> intersection = new ArrayList<>(matchDataList.get(0));

        for (List<Ingredient> list: matchDataList) {
            intersection.retainAll(list);
        }
        return intersection;
    }

    public void editAll(Map<String, Integer> ingrMap, int k){
        ingrMap.forEach((key, value) -> {
            try {
                Ingredient currentIngr = ingredientRepository.findByName(key);
                currentIngr.setAmount(currentIngr.getAmount()-value * k);
                ingredientRepository.save(currentIngr);
            }
            catch (Exception e){
                log.error("НЕ УДАЛОСЬ ИЗМЕНИТЬ ЧИСЛО ИНГРЕДИЕНТОВ");
            }

        });
    }

    //можно добавить обновление по конкретному игнред
    public void updateAllDishesStatus(){
        List<Dish> dishesList = dishRepository.findAll();
        for (Dish dish: dishesList){
            try {
                dish.setAvailable(checkDishIngs(dish));
                dishRepository.save(dish);
            }catch (Exception e){
                log.error("ERROR UPDATING DISHES STATUS " + dish.toString() + " EXC: " + e);
            }
        }
    }

    public boolean checkDishIngs(Dish dish){
        Map<String, Integer> ingredientsAmounts = dish.getIngredientsAmounts();
        for (Map.Entry<String, Integer> pair : ingredientsAmounts.entrySet()) {
            Ingredient ingDB = ingredientRepository.findByName(pair.getKey());
            if(ingDB == null || ingDB.getAmount() <= 0)
                return false;
        }
        return true;
    }

}
