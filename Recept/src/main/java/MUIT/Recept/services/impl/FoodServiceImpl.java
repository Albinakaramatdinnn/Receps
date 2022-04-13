package MUIT.Recept.services.impl;

import MUIT.Recept.entities.Food;
import MUIT.Recept.repositories.FoodRepository;
import MUIT.Recept.services.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    FoodRepository foodRepository;

    @Override
    public Food getName(String name) {
        return foodRepository.findFoodByNameAndDeletedAtNull(name);
    }

    @Override
    public void deleteFood(Food food) {
        food.setDeletedAt(new Date());
        foodRepository.save(food);
    }

    @Override
    public Food saveFood(Food food) {
        return foodRepository.save(food);
    }

    @Override
    public Food getFood(Long id) {
        return foodRepository.findFoodByDeletedAtNullAndId(id);
    }

    @Override
    public List<Food> getAllFoods() {
        return foodRepository.findByDeletedAtNull();
    }

    @Override
    public Food updateAvatar(Food food) {
        Food fd = foodRepository.findByIdAndAmountGreaterThan(food.getId(),0);

        if(fd!=null){
            fd.setAvatar(fd.getAvatar());
            return foodRepository.save(fd);
        }
        return null;

    }
}
