package MUIT.Recept.services;

import MUIT.Recept.entities.Food;

import java.util.List;

public interface FoodService {

    Food updateAvatar(Food food);

    void deleteFood(Food food);

    Food saveFood(Food food);

    Food getFood(Long id);

    List<Food> getAllFoods();

    Food getName(String name);
}
