package MUIT.Recept.services;

import MUIT.Recept.entities.Categories;
import MUIT.Recept.entities.Food;

import java.util.List;

public interface CategoryService {

    List<Categories> getAllCategory();

    Categories getCategory(Long id);

}
